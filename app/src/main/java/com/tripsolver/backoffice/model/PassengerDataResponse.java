package com.tripsolver.backoffice.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 4/27/2019.
 */

public class PassengerDataResponse {
    @SerializedName("Title")
    private String Title;
    @SerializedName("Pax_Type")
    private String Pax_Type;
    @SerializedName("FirstName")
    private String FirstName;
    @SerializedName("MiddleName")
    private String MiddleName;
    @SerializedName("LastName")
    private String LastName;
    @SerializedName("Gender")
    private String Gender;
    @SerializedName("DateofBirth")
    private String DateofBirth;
    @SerializedName("MealRequest")
    private String MealRequest;
    @SerializedName("SpecialRequest")
    private String SpecialRequest;
    @SerializedName("FrequentFlyer")
    private String FrequentFlyer;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPax_Type() {
        return Pax_Type;
    }

    public void setPax_Type(String pax_Type) {
        Pax_Type = pax_Type;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDateofBirth() {
        return DateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        DateofBirth = dateofBirth;
    }

    public String getMealRequest() {
        return MealRequest;
    }

    public void setMealRequest(String mealRequest) {
        MealRequest = mealRequest;
    }

    public String getSpecialRequest() {
        return SpecialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        SpecialRequest = specialRequest;
    }

    public String getFrequentFlyer() {
        return FrequentFlyer;
    }

    public void setFrequentFlyer(String frequentFlyer) {
        FrequentFlyer = frequentFlyer;
    }


}
