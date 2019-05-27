package com.tripsolver.backoffice.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lenovo on 4/30/2019.
 */

public class FlightsDatalistResponse {
    @SerializedName("Stops")
    private String Stops;

    public String getValidatecarrier() {
        return validatecarrier;
    }

    public void setValidatecarrier(String validatecarrier) {
        this.validatecarrier = validatecarrier;
    }

    @SerializedName("validatingcarrier")
    private String validatecarrier;

    @SerializedName("journeytime")
    private String journeytime;
    @SerializedName("FlightSegments")
    private List<ItineraryDataResponse> FlightSegments;

    public String getStops() {
        return Stops;
    }

    public void setStops(String stops) {
        Stops = stops;
    }

    public String getJourneytime() {
        return journeytime;
    }

    public void setJourneytime(String journeytime) {
        this.journeytime = journeytime;
    }

    public List<ItineraryDataResponse> getFlightSegments() {
        return FlightSegments;
    }

    public void setFlightSegments(List<ItineraryDataResponse> flightSegments) {
        FlightSegments = flightSegments;
    }


}
