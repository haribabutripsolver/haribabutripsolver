package com.tripsolver.backoffice.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 4/27/2019.
 */

public class PaymenInfoResponse {

    @SerializedName("CardType")
    private String CardType;

    public String getCardholdername() {
        return cardholdername;
    }

    public void setCardholdername(String cardholdername) {
        this.cardholdername = cardholdername;
    }

    public String getVerificationnumber() {
        return verificationnumber;
    }

    public void setVerificationnumber(String verificationnumber) {
        this.verificationnumber = verificationnumber;
    }

    @SerializedName("CardNumber")
    private String CardNumber;



    @SerializedName("CardHoldersName")
    private String cardholdername;


    @SerializedName("VerificationNumber")
    private String verificationnumber;

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getExp_month() {
        return Exp_month;
    }

    public void setExp_month(String exp_month) {
        Exp_month = exp_month;
    }

    public String getExp_year() {
        return Exp_year;
    }

    public void setExp_year(String exp_year) {
        Exp_year = exp_year;
    }

    public String getStreetAddress() {
        return StreetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        StreetAddress = streetAddress;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    @SerializedName("Exp_month")
    private String Exp_month;

    @SerializedName("Exp_year")
    private String Exp_year;

    @SerializedName("StreetAddress")
    private String StreetAddress;

    @SerializedName("City")
    private String City;

    @SerializedName("State")
    private String State;

    @SerializedName("Country")
    private String Country;

    @SerializedName("ZipCode")
    private String ZipCode;


}
