package com.tripsolver.backoffice.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 4/26/2019.
 */

public class FareDetResponse {


    public String getType() {
        return Type;
    }
    @SerializedName("PaxTypeCode")
    private String paxcodetype;

    public void setType(String type) {
        Type = type;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getBaseFare() {
        return BaseFare;
    }

    public void setBaseFare(String baseFare) {
        BaseFare = baseFare;
    }

    public String getTax() {
        return Tax;
    }

    public void setTax(String tax) {
        Tax = tax;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }


    @SerializedName("Type")
    private String Type;
    @SerializedName("Quantity")
    private String Quantity;
    @SerializedName("Currency")
    private String Currency;
    @SerializedName("BaseFare")
    private String BaseFare;
    @SerializedName("Tax")
    private String Tax;
    @SerializedName("Discount")
    private String Discount;
    @SerializedName("Total")
    private String Total;

    public String getPaxcodetype() {
        return paxcodetype;
    }

    public void setPaxcodetype(String paxcodetype) {
        this.paxcodetype = paxcodetype;
    }


}
