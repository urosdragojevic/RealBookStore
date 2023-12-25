package com.urosdragojevic.realbookstore.domain;

public class Comment {
    private int bookId;
    private int userId;
    private String comment;

    public Comment() {
    }

    public Comment(int bookId, int userId, String comment) {
        this.bookId = bookId;
        this.userId = userId;
        this.comment = comment;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int carId) {
        this.bookId = carId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "bookId=" + bookId +
                ", userId=" + userId +
                ", comment='" + comment + '\'' +
                '}';
    }
}
