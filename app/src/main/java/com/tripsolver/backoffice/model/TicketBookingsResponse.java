package com.tripsolver.backoffice.model;

import com.google.gson.annotations.SerializedName;
import com.tripsolver.backoffice.R;

/**
 * Created by lenovo on 4/24/2019.
 */

public class TicketBookingsResponse {
String colorvalue="#3775dd";
    public String getFbid() {
        return fbid;
    }

    public void setFbid(String fbid) {
        this.fbid = fbid;
    }



    public String getConfirmationid() {
        return confirmationid;
    }

    public void setConfirmationid(String confirmationid) {
        this.confirmationid = confirmationid;
    }

    public String getPnrvalue() {
        return pnrvalue;
    }

    public void setPnrvalue(String pnrvalue) {
        this.pnrvalue = pnrvalue;
    }

    public boolean isTotalfare() {
        return totalfare;
    }

    public void setTotalfare(boolean totalfare) {
        this.totalfare = totalfare;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhonevalue() {
        return phonevalue;
    }

    public void setPhonevalue(String phonevalue) {
        this.phonevalue = phonevalue;
    }

    public String getEmailvalue() {
        return emailvalue;
    }

    public void setEmailvalue(String emailvalue) {
        this.emailvalue = emailvalue;
    }

    public String getCreateddatetime() {
        return createddatetime;
    }

    public void setCreateddatetime(String createddatetime) {
        this.createddatetime = createddatetime;
    }

    public boolean isLastupdateddate() {
        return lastupdateddate;
    }

    public void setLastupdateddate(boolean lastupdateddate) {
        this.lastupdateddate = lastupdateddate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getLastupdatedby() {
        return lastupdatedby;
    }

    public void setLastupdatedby(String lastupdatedby) {
        this.lastupdatedby = lastupdatedby;
    }

    @SerializedName("Fb_id")
    private String fbid;

    public String getColorvalue() {
        return colorvalue;
    }

    public void setColorvalue(String colorvalue) {
        this.colorvalue = colorvalue;
    }

    @SerializedName("Colorvalue")

    public String getBookingstatus() {
        return bookingstatus;
    }

    public void setBookingstatus(String bookingstatus) {
        this.bookingstatus = bookingstatus;
    }

    @SerializedName("Booking_Status")
    private String bookingstatus;


    public String getSourcetype() {
        return sourcetype;
    }

    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype;
    }

    @SerializedName("Source")
    private String sourcetype;

    @SerializedName("Confirmationid")
    private String confirmationid;
    @SerializedName("PNR")
    private String pnrvalue;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @SerializedName("Created")
    private String created;

    public String getFromcity() {
        return fromcity;
    }

    public void setFromcity(String fromcity) {
        this.fromcity = fromcity;
    }

    public String getTocity() {
        return tocity;
    }

    public void setTocity(String tocity) {
        this.tocity = tocity;
    }

    public String getTriptype() {
        return triptype;
    }

    public void setTriptype(String triptype) {
        this.triptype = triptype;
    }

    @SerializedName("From")
    private String fromcity;
    @SerializedName("To")
    private String tocity;
    @SerializedName("Trip_Type")
    private String triptype;

    @SerializedName("Total_fare")
    private boolean totalfare;
    @SerializedName("First_Name")
    private String firstname;
    @SerializedName("Last_Name")
    private String lastname;

    @SerializedName("Phone")
    private String phonevalue;
    @SerializedName("Email")
    private String emailvalue;
    @SerializedName("Creation_Date_Time")
    private String createddatetime;

    @SerializedName("Last_updated_date")
    private boolean lastupdateddate;
    @SerializedName("Created_by")
    private String createdby;
    @SerializedName("Last_updated_by")
    private String lastupdatedby;

    public String getBookingid() {
        return Bookingid;
    }

    public void setBookingid(String bookingid) {
        Bookingid = bookingid;
    }

    @SerializedName("Bookingid")
    private String Bookingid;


    public String getItinselid() {
        return itinselid;
    }

    public void setItinselid(String itinselid) {
        this.itinselid = itinselid;
    }

    @SerializedName("itin_sel_id")
    private String itinselid;



}
