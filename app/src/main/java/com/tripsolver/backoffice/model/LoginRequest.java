package com.tripsolver.backoffice.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 4/24/2019.
 */


public class LoginRequest {
    @SerializedName("username")
    private String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @SerializedName("password")
    private String password;
    @SerializedName("bof_name")
    private String bof_name;
    @SerializedName("user_ip")
    private String userip;


    public LoginRequest(String username,String password, String bofname, String userid, String userip) {
        this.username = username;
        this.password = password;
        this.bof_name = bofname;

    }


}
