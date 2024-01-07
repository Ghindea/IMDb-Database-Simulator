package org.example;

public class Rating {
    private String userName, comment;
    private int rating; // integer from [1, 10]
    Rating(String userName, int rating, String comment) {
        this.userName   = userName;
        this.rating     = rating;
        this.comment    = comment;
    }
    Rating() {}
    public String getUserName() {
        return userName;
    }
    public String getComment() {
        return comment;
    }
    public int getRating() {
        return rating;
    }

    public Rating setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Rating setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Rating setRating(int rating) {
        this.rating = rating;
        return this;
    }
}
