package com.development.community;

import android.location.Location;

public class CommUnityLocation extends Location {
    public CommUnityLocation(String provider) {
        super(provider);
    }

    public CommUnityLocation(Location l) {
        super(l);
    }

    public CommUnityLocation(){
        super("");
    }

}
