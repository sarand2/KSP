/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ksp.Couriers;

import java.util.Map;

/**
 *
 * @author Admin
 */
public class Location {
    String address;
    Double lon;
    Double lat;

    public Location(String address, Double lon, Double lat) {
        this.address = address;
        this.lon = lon;
        this.lat = lat;
    }
    public Location(String address){
        this.address = address; 
        OpenStreetMapUtils osmu = OpenStreetMapUtils.getInstance();
        Map<String, Double> coords = OpenStreetMapUtils.getInstance().getCoordinates(address);
        this.lon = coords.get("lon");
        this.lat = coords.get("lat");
    }
    public Location(){}
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "Location{" + "address=" + address + ", lon=" + lon + ", lat=" + lat + '}';
    }

    
    

}
