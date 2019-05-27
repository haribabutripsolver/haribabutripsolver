package com.tripsolver.backoffice.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 5/3/2019.
 */

public class ActivityCountResponse {
    @SerializedName("BookingStarted")
    private String BookingStarted;

    public String getBookingStarted() {
        return BookingStarted;
    }

    public void setBookingStarted(String bookingStarted) {
        BookingStarted = bookingStarted;
    }

    public String getConfirmed() {
        return Confirmed;
    }

    public void setConfirmed(String confirmed) {
        Confirmed = confirmed;
    }

    public String getTicketed() {
        return Ticketed;
    }

    public void setTicketed(String ticketed) {
        Ticketed = ticketed;
    }

    public String getCancelled() {
        return Cancelled;
    }

    public void setCancelled(String cancelled) {
        Cancelled = cancelled;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @SerializedName("Confirmed")
    private String Confirmed;
    @SerializedName("Ticketed")
    private String Ticketed;
    @SerializedName("Cancelled")
    private String Cancelled;
    @SerializedName("Message")
    private String Message;

}
