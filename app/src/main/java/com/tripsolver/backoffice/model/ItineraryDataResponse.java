package com.tripsolver.backoffice.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 4/29/2019.
 */

public class ItineraryDataResponse {
    @SerializedName("ValidatingCarrier")
    private String ValidatingCarrier;

    public String getClasstype() {
        return classtype;
    }

    public void setClasstype(String classtype) {
        this.classtype = classtype;
    }

    @SerializedName("classtype")
    private String classtype;
    public String getValidatingCarrier() {
        return ValidatingCarrier;
    }

    public void setValidatingCarrier(String validatingCarrier) {
        ValidatingCarrier = validatingCarrier;
    }

    public String getJourneytime() {
        return journeytime;
    }

    public void setJourneytime(String journeytime) {
        this.journeytime = journeytime;
    }

    public String getStops() {
        return Stops;
    }

    public void setStops(String stops) {
        Stops = stops;
    }

    public String getSrcdepttime() {
        return srcdepttime;
    }

    public void setSrcdepttime(String srcdepttime) {
        this.srcdepttime = srcdepttime;
    }

    public String getDestarrvtime() {
        return destarrvtime;
    }

    public void setDestarrvtime(String destarrvtime) {
        this.destarrvtime = destarrvtime;
    }

    public String getElapsedtime() {
        return elapsedtime;
    }

    public void setElapsedtime(String elapsedtime) {
        this.elapsedtime = elapsedtime;
    }

    public String getDepcitycode() {
        return depcitycode;
    }

    public void setDepcitycode(String depcitycode) {
        this.depcitycode = depcitycode;
    }

    public String getArrcitycode() {
        return arrcitycode;
    }

    public void setArrcitycode(String arrcitycode) {
        this.arrcitycode = arrcitycode;
    }


    public String getFlightnumberarr() {
        return flightnumberarr;
    }

    public void setFlightnumberarr(String flightnumberarr) {
        this.flightnumberarr = flightnumberarr;
    }

    @SerializedName("journeytime")
    private String journeytime;
    @SerializedName("Stops")
    private String Stops;
    @SerializedName("srcdepttime")
    private String srcdepttime;
    @SerializedName("destarrvtime")
    private String destarrvtime;


    public String getSrcdatetime() {
        return srcdatetime;
    }

    public void setSrcdatetime(String srcdatetime) {
        this.srcdatetime = srcdatetime;
    }

    public String getDestdatetime() {
        return destdatetime;
    }

    public void setDestdatetime(String destdatetime) {
        this.destdatetime = destdatetime;
    }

    @SerializedName("srcdatetime")
    private String srcdatetime;
    @SerializedName("destdatetime")
    private String destdatetime;



    @SerializedName("ElapsedTime")
    private String elapsedtime;
    @SerializedName("depcitycode")
    private String depcitycode;
    @SerializedName("arrcitycode")
    private String arrcitycode;

    @SerializedName("flightnumberarr")
    private String flightnumberarr;

    public String getFlightdetdestarrvtime() {
        return flightdetdestarrvtime;
    }

    public void setFlightdetdestarrvtime(String flightdetdestarrvtime) {
        this.flightdetdestarrvtime = flightdetdestarrvtime;
    }

    public String getFlightdetsrcdepttime() {
        return flightdetsrcdepttime;
    }

    public void setFlightdetsrcdepttime(String flightdetsrcdepttime) {
        this.flightdetsrcdepttime = flightdetsrcdepttime;
    }

    @SerializedName("flightdetdestarrvtime")
    private String flightdetdestarrvtime;
    @SerializedName("flightdetsrcdepttime")
    private String flightdetsrcdepttime;

    public String getSrcairport() {
        return srcairport;
    }

    public void setSrcairport(String srcairport) {
        this.srcairport = srcairport;
    }

    public String getArrivairport() {
        return arrivairport;
    }

    public void setArrivairport(String arrivairport) {
        this.arrivairport = arrivairport;
    }

    public String getOperationalairline() {
        return operationalairline;
    }

    public void setOperationalairline(String operationalairline) {
        this.operationalairline = operationalairline;
    }

    public String getSrccityname() {
        return srccityname;
    }

    public void setSrccityname(String srccityname) {
        this.srccityname = srccityname;
    }

    public String getArrcityname() {
        return arrcityname;
    }

    public void setArrcityname(String arrcityname) {
        this.arrcityname = arrcityname;
    }

    public String getArrivdate() {
        return arrivdate;
    }

    public void setArrivdate(String arrivdate) {
        this.arrivdate = arrivdate;
    }

    public String getSrcdate() {
        return srcdate;
    }

    public void setSrcdate(String srcdate) {
        this.srcdate = srcdate;
    }

    @SerializedName("srccityname")
    private String srccityname;
    @SerializedName("arrcityname")
    private String arrcityname;
    @SerializedName("arrivdate")
    private String arrivdate;

    @SerializedName("srcdate")
    private String srcdate;
    @SerializedName("srcairport")
    private String srcairport;
    @SerializedName("arrivairport")
    private String arrivairport;
    @SerializedName("operatingairline")
    private String operationalairline;

}
