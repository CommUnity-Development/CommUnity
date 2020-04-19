package com.development.community;

import androidx.annotation.NonNull;

public class LatLng {
    private double lat;
    private double lng;

    public LatLng(){
        lat = 0;
        lng = 0;
    }

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
