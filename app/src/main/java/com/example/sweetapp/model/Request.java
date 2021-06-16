package com.example.sweetapp.model;

import com.example.sweetapp.R;

public class Request {
    String key,chaletRequired,phone,theNameOrder,bookingDate,bookingPeriod,status;

    public Request() {
    }

    public Request(String key,String chaletRequired,String phone,String status, String theNameOrder, String bookingDate, String bookingPeriod) {
        this.key = key;
        this.chaletRequired = chaletRequired;
        this.phone = phone;
        this.status = status;
        this.theNameOrder = theNameOrder;
        this.bookingDate = bookingDate;
        this.bookingPeriod = bookingPeriod;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getChaletRequired() {
        return chaletRequired;
    }

    public void setChaletRequired(String chaletRequired) {
        this.chaletRequired = chaletRequired;
    }

    public String getTheNameOrder() {
        return theNameOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTheNameOrder(String theNameOrder) {
        this.theNameOrder = theNameOrder;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingPeriod() {
        return bookingPeriod;
    }

    public void setBookingPeriod(String bookingPeriod) {
        this.bookingPeriod = bookingPeriod;
    }
}
