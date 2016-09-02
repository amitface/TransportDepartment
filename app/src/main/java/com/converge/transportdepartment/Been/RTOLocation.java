package com.converge.transportdepartment.Been;

import java.util.ArrayList;

/**
 * Created by cnvgpc on 2/9/16.
 */
public class RTOLocation {

    private String rtoName, rtoAddress, rtoCode;
    private RTOLatLng rtoLatLng;

    public RTOLocation(String rtoName, String rtoAddress, String rtoCode, RTOLatLng rtoLatLng) {
        this.rtoName = rtoName;
        this.rtoAddress = rtoAddress;
        this.rtoCode = rtoCode;
        this.rtoLatLng = rtoLatLng;
    }

    public RTOLocation() {
    }

    public String getRtoName() {
        return rtoName;
    }

    public void setRtoName(String rtoName) {
        this.rtoName = rtoName;
    }

    public String getRtoAddress() {
        return rtoAddress;
    }

    public void setRtoAddress(String rtoAddress) {
        this.rtoAddress = rtoAddress;
    }

    public String getRtoCode() {
        return rtoCode;
    }

    public void setRtoCode(String rtoCode) {
        this.rtoCode = rtoCode;
    }

    public RTOLatLng getRtoLatLng() {
        return rtoLatLng;
    }

    public void setRtoLatLng(RTOLatLng rtoLatLng) {
        this.rtoLatLng = rtoLatLng;
    }


    public static ArrayList<RTOLocation> getAllRTOLocations(ArrayList<String> rtoNameArrayList,
                                                            ArrayList<String> rtoCodeArrayList,
                                                            ArrayList<String> rtoLocArrayList,
                                                            ArrayList<String> rtoLatLngArrayList ) {
        ArrayList<RTOLocation> allLocations = new ArrayList<>();

        for(int item = 0;item < rtoNameArrayList.size();item++) {
            RTOLocation tempObj = new RTOLocation(
                    rtoNameArrayList.get(item),
                    rtoCodeArrayList.get(item),
                    rtoLocArrayList.get(item),
                    new RTOLatLng(rtoLatLngArrayList.get(item))
            );

            allLocations.add(tempObj);
        }

        return allLocations;
    }

}
