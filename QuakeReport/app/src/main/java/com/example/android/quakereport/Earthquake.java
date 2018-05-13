package com.example.android.quakereport;

/**
 * Created by karolgrzesiak on 24.07.17.
 */

public class Earthquake {
    private String mLocation;
    private double mMagnitude;
    private long mTimeInMiliseconds;
    private String mUrl;

    public Earthquake(String location, double magnitude, long timeInMiliseconds, String url){
        mLocation = location;
        mMagnitude = magnitude;
        mTimeInMiliseconds = timeInMiliseconds;
        mUrl=url;
    }

    public String getLocation(){
        return mLocation;
    }
    public double getMagnitude(){
        return mMagnitude;
    }
    public long getTimeInMiliseconds(){
        return mTimeInMiliseconds;
    }

    public String getUrl(){
        return mUrl;
    }
}
