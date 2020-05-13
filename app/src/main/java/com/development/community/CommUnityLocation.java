package com.development.community;

import android.location.Location;

/**
 * A location class with new constructors which allow it to work more effectively with Firebase.
 */
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

    @Override
    public boolean isFromMockProvider() {
        return super.isFromMockProvider();
    }

}
