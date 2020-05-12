package com.development.community;

import androidx.annotation.NonNull;

/**
 * A class that stores latitude and longitude values
 */
public class LatLng {
    private double lat;
    private double lng;

    /**
     * The default constructor
     */
    public LatLng(){
        lat = 0;
        lng = 0;
    }

    /**
     * The constructor with parameters
     * @param lat the latitude for the location
     * @param lng the longitude for the location
     */
    public LatLng(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }

    @Override @NonNull
    public String toString(){
        return "Lat: "+lat +", Lng: "+lng;
    }

    public double getLat(){
        return lat;
    }
    public double getLng(){
        return lng;
    }
    public void setLat(double a){
        lat = a;
    }
    public void setLng(double a){
        lng = a;
    }
}
