package com.tripsolver.backoffice.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 4/27/2019.
 */

public class CustomerDataResponse {
    @SerializedName("FirstName")
    private String FirstName;

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @SerializedName("LastName")
    private String LastName;
    @SerializedName("Phone")
    private String Phone;
    @SerializedName("Email")
    private String Email;
}
