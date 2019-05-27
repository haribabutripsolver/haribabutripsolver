package com.tripsolver.backoffice.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 4/27/2019.
 */

public class ReportsDataResponse {
    @SerializedName("CardNumber")
    private String CardNumber;

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getBkRefid() {
        return BkRefid;
    }

    public void setBkRefid(String bkRefid) {
        BkRefid = bkRefid;
    }

    public String getPNR() {
        return PNR;
    }

    public void setPNR(String PNR) {
        this.PNR = PNR;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAirline() {
        return Airline;
    }

    public void setAirline(String airline) {
        Airline = airline;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }





    public String getTriptype() {
        return Triptype;
    }

    public void setTriptype(String triptype) {
        Triptype = triptype;
    }

    public String getTotalpax() {
        return Totalpax;
    }

    public void setTotalpax(String totalpax) {
        Totalpax = totalpax;
    }

    public String getTotalfare() {
        return Totalfare;
    }

    public void setTotalfare(String totalfare) {
        Totalfare = totalfare;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getBookingdate() {
        return Bookingdate;
    }

    public void setBookingdate(String bookingdate) {
        Bookingdate = bookingdate;
    }

    @SerializedName("Bookingdate")
    private String Bookingdate;

    public String getBookingtype() {
        return bookingtype;
    }

    public void setBookingtype(String bookingtype) {
        this.bookingtype = bookingtype;
    }

    @SerializedName("bookingtype")
    private String bookingtype;
    @SerializedName("BkRefid")
    private String BkRefid;

    @SerializedName("PNR")
    private String PNR;

    @SerializedName("Name")
    private String Name;

    @SerializedName("Status")
    private String Status;

    @SerializedName("Airline")
    private String Airline;

    @SerializedName("From")
    private String From;

    @SerializedName("To")
    private String To;



    @SerializedName("Triptype")
    private String Triptype;

    @SerializedName("Totalpax")
    private String Totalpax;

    @SerializedName("Totalfare")
    private String Totalfare;

    @SerializedName("Source")
    private String Source;

    public String getClasstype() {
        return classtype;
    }

    public void setClasstype(String classtype) {
        this.classtype = classtype;
    }

    @SerializedName("Class")
    private String classtype;

}
