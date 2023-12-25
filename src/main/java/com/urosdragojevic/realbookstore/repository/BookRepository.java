package com.urosdragojevic.realbookstore.repository;

import com.urosdragojevic.realbookstore.audit.AuditLogger;
import com.urosdragojevic.realbookstore.domain.Book;
import com.urosdragojevic.realbookstore.domain.Genre;
import com.urosdragojevic.realbookstore.domain.NewBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {

    private static final Logger LOG = LoggerFactory.getLogger(BookRepository.class);
    private static final AuditLogger auditLogger = AuditLogger.getAuditLogger(BookRepository.class);

    private DataSource dataSource;

    public BookRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Book> getAll() {
        List<Book> bookList = new ArrayList<>();
        String query = "SELECT id, title, description, author FROM books";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                Book book = createBookFromResultSet(rs);
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public List<Book> search(String searchTerm) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        String query = "SELECT DISTINCT b.id, b.title, b.description, b.author FROM books b, books_to_genres bg, genres g" +
                " WHERE b.id = bg.bookId" +
                " AND bg.genreId = g.id" +
                " AND (UPPER(b.title) like UPPER('%" + searchTerm + "%')" +
                " OR UPPER(g.name) like UPPER('%" + searchTerm + "%'))";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                bookList.add(createBookFromResultSet(rs));
            }
        }
        return bookList;
    }

    public Book get(int bookId) {
        String query = "SELECT id, title, description, author, FROM books WHERE books.id = " + bookId;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            if (rs.next()) {
                return createBookFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long create(NewBook book, List<Genre> genresToInsert) {
        String query = "INSERT INTO books(title, description, author) VALUES(?, ?, ?)";
        long id = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getDescription());
            statement.setString(3, book.getAuthor());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                long finalId = id;
                genresToInsert.stream().forEach(genre -> {
                    String query2 = "INSERT INTO books_to_genres(bookId, genreId) VALUES (?, ?)";
                    try (PreparedStatement statement2 = connection.prepareStatement(query2);
                    ) {
                        statement2.setInt(1, (int) finalId);
                        statement2.setInt(2, genre.getId());
                        statement2.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void delete(int bookId) {
        String query = "DELETE FROM books WHERE id = " + bookId;
        String query2 = "DELETE FROM ratings WHERE bookId = " + bookId;
        String query3 = "DELETE FROM comments WHERE bookId = " + bookId;
        String query4 = "DELETE FROM books_to_genres WHERE bookId = " + bookId;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(query);
            statement.executeUpdate(query2);
            statement.executeUpdate(query3);
            statement.executeUpdate(query4);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Book createBookFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String title = rs.getString(2);
        String description = rs.getString(3);
        String author = rs.getString(4);
        return new Book(id, title, description, author);
    }

    private void updateBookFromResultSet(Book book, ResultSet rs) throws SQLException {
        book.setId(rs.getInt(1));
        book.setTitle(rs.getString(2));
        book.setDescription(rs.getString(3));
        book.setAuthor(rs.getString(4));
        book.getGenres().add(new Genre(rs.getInt(5), rs.getString(6)));
    }

    private Genre createGenreFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String name = rs.getString(2);
        return new Genre(id, name);
    }
}
