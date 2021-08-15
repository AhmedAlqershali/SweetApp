package com.example.sweetapp.model;

import com.example.sweetapp.R;

public class Request {
 String chaletId, name_chalet, name_applicant, booking_date, Booking_period,status;
    public Request() {
    }

    public Request(String chaletId, String name_chalet, String name_applicant, String booking_date, String booking_period,String status) {
        this.chaletId = chaletId;
        this.name_chalet = name_chalet;
        this.name_applicant = name_applicant;
        this.booking_date = booking_date;
        Booking_period = booking_period;
        this.status = status;
    }

    public String getChaletId() {
        return chaletId;
    }

    public void setChaletId(String chaletId) {
        this.chaletId = chaletId;
    }

    public String getName_chalet() {
        return name_chalet;
    }

    public void setName_chalet(String name_chalet) {
        this.name_chalet = name_chalet;
    }

    public String getName_applicant() {
        return name_applicant;
    }

    public void setName_applicant(String name_applicant) {
        this.name_applicant = name_applicant;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public String getBooking_period() {
        return Booking_period;
    }

    public void setBooking_period(String booking_period) {
        Booking_period = booking_period;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
