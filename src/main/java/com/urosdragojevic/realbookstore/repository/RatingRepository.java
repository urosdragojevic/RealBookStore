package com.urosdragojevic.realbookstore.repository;

import com.urosdragojevic.realbookstore.domain.Rating;
import com.urosdragojevic.realbookstore.audit.AuditLogger;
import com.urosdragojevic.realbookstore.audit.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RatingRepository {

    private static final Logger LOG = LoggerFactory.getLogger(RatingRepository.class);
    private static final AuditLogger auditLogger = AuditLogger.getAuditLogger(RatingRepository.class);
    private DataSource dataSource;

    public RatingRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createOrUpdate(Rating rating) {
        String query = "SELECT bookId, userId, rating FROM ratings WHERE bookId = " + rating.getBookId() + " AND userID = " + rating.getUserId();
        String query2 = "update ratings SET rating = ? WHERE bookId = ? AND userId = ?";
        String query3 = "insert into ratings(bookId, userId, rating) values (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)
        ) {
            if (rs.next()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(query2)) {
                    preparedStatement.setInt(1, rating.getRating());
                    preparedStatement.setInt(2, rating.getBookId());
                    preparedStatement.setInt(3, rating.getUserId());
                    preparedStatement.executeUpdate();

                    auditLogger.audit("User with id : " + rating.getUserId() + "updated book with id : " + rating.getBookId());
                }
            } else {
                try (PreparedStatement preparedStatement = connection.prepareStatement(query3)) {
                    preparedStatement.setInt(1, rating.getBookId());
                    preparedStatement.setInt(2, rating.getUserId());
                    preparedStatement.setInt(3, rating.getRating());
                    preparedStatement.executeUpdate();

                    auditLogger.audit("User with id : " + rating.getUserId() + "add book with id: " + rating.getBookId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.error("There was a problem while creating or updating rating for book with id: , user: " +  rating.getBookId());
        }
    }

    public List<Rating> getAll(int bookId) {
        List<Rating> ratingList = new ArrayList<>();
        String query = "SELECT bookId, userId, rating FROM ratings WHERE bookId = " + bookId;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                ratingList.add(new Rating(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.error("There was a problem while retrieving ratings for book with id: ", bookId);
        }
        return ratingList;
    }

    public float getRatingForBook(int bookId) {
        List<Rating> allRatings = getAll(bookId);
        if (allRatings.isEmpty()) {
            return 0;
        }
        return (float) allRatings.stream().map((Rating::getRating)).reduce(Integer::sum).orElseThrow() / (long) allRatings.size();
    }
}
