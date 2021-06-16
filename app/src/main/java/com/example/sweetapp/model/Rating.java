package com.example.sweetapp.model;

public class Rating {
    String userPhone;
    String chaletId;
    String ratingValue;
    String Comment;
//    String uid;


    public Rating() {
    }

    public Rating(String userPhone, String chaletId, String ratingValue, String comment) {
        this.userPhone = userPhone;
        this.chaletId = chaletId;
        this.ratingValue = ratingValue;
        Comment = comment;
//        this.uid = uid;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getChaletId() {
        return chaletId;
    }

    public void setChaletId(String chaletId) {
        this.chaletId = chaletId;
    }

    public String getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(String ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }



}
