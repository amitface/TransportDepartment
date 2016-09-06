package com.converge.transportdepartment.Been;

/**
 * Created by cnvgpc on 2/9/16.
 */
public class RTOLatLng {

    private double latitute, longitude;

    public RTOLatLng() {
        this.latitute = 0.0;
        this.longitude = 0.0;
    }

    public RTOLatLng(double latitute, double longitude) {
        this.latitute = latitute;
        this.longitude = longitude;
    }

    public RTOLatLng(String latLngStr) {
        //THIS constructor is for , comma separated Lat,Lng
        String[] latLng = latLngStr.split(",");

        this.latitute = Double.valueOf(latLng[0]);
        this.longitude = Double.valueOf(latLng[0]);
    }

    public double getLatitute() {
        return latitute;
    }

    public void setLatitute(double latitute) {
        this.latitute = latitute;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatLng(String latLngStr) {
        //THIS constructor is for , comma separated Lat,Lng
        String[] latLng = latLngStr.split(",");

        this.latitute = Double.valueOf(latLng[0]);
        this.longitude = Double.valueOf(latLng[0]);
    }


}
