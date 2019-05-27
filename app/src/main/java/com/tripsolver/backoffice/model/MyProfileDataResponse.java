package com.tripsolver.backoffice.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 5/1/2019.
 */

public class MyProfileDataResponse {
    @SerializedName("Agency_Name")
    private String agencyname;
    @SerializedName("Agency_Url")
    private String agencyurl;
    @SerializedName("Back_office_name")
    private String backofficename;
    @SerializedName("Agency_Url_bk")
    private String agencyurlbk;
    @SerializedName("Help_Desk_Link")
    private String helpdesklink;
    @SerializedName("FirstName")
    private String firstname;
    @SerializedName("MiddleName")
    private String middlename;
    @SerializedName("LastName")
    private String lastname;
    @SerializedName("Phone")
    private String phone;
    @SerializedName("TollFreeNumber")
    private String tollfreenumber;
    @SerializedName("FaxNumber")
    private String faxnumber;
    @SerializedName("Email")
    private String email;
    @SerializedName("ContactRole")
    private String contactrole;
    @SerializedName("Street")
    private String street;
    @SerializedName("Branch")
    private String branch;
    @SerializedName("City")
    private String city;
    @SerializedName("State")
    private String state;

    public String getAgencyname() {
        return agencyname;
    }

    public void setAgencyname(String agencyname) {
        this.agencyname = agencyname;
    }

    public String getAgencyurl() {
        return agencyurl;
    }

    public void setAgencyurl(String agencyurl) {
        this.agencyurl = agencyurl;
    }

    public String getBackofficename() {
        return backofficename;
    }

    public void setBackofficename(String backofficename) {
        this.backofficename = backofficename;
    }

    public String getAgencyurlbk() {
        return agencyurlbk;
    }

    public void setAgencyurlbk(String agencyurlbk) {
        this.agencyurlbk = agencyurlbk;
    }

    public String getHelpdesklink() {
        return helpdesklink;
    }

    public void setHelpdesklink(String helpdesklink) {
        this.helpdesklink = helpdesklink;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTollfreenumber() {
        return tollfreenumber;
    }

    public void setTollfreenumber(String tollfreenumber) {
        this.tollfreenumber = tollfreenumber;
    }

    public String getFaxnumber() {
        return faxnumber;
    }

    public void setFaxnumber(String faxnumber) {
        this.faxnumber = faxnumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactrole() {
        return contactrole;
    }

    public void setContactrole(String contactrole) {
        this.contactrole = contactrole;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @SerializedName("Country")
    private String country;
    @SerializedName("ZipCode")
    private String zipcode;





}
