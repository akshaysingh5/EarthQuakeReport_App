package com.example.android.quakereport;

import java.util.Date;

public class EarthQuake_data {

     private double magnitude;
     private String place;
    private Date date;
    private String mUrl;

    public EarthQuake_data( double magnitude,String place,Date date,String url)
    {
       this.magnitude=magnitude;
        this.place=place;
        this.date=date;
        this.mUrl=url;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }

    public String getUrl() {
        return mUrl;
    }

    public Date getDate() {
        return date;
    }
}
