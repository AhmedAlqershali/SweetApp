package com.example.sweetapp.model;

public class ServiceModel {
    boolean SwimmingPoolMen ,SwimmingPoolSmall,Stadium,Wifi,Billiard,Kitchen,Garage,Stereo,TennisTable;

    public ServiceModel() {
    }

    public ServiceModel(boolean swimmingPoolMen, boolean swimmingPoolSmall, boolean stadium, boolean wifi, boolean billiard, boolean kitchen, boolean garage, boolean stereo, boolean tennisTable) {
        SwimmingPoolMen = swimmingPoolMen;
        SwimmingPoolSmall = swimmingPoolSmall;
        Stadium = stadium;
        Wifi = wifi;
        Billiard = billiard;
        Kitchen = kitchen;
        Garage = garage;
        Stereo = stereo;
        TennisTable = tennisTable;
    }

    public boolean isSwimmingPoolMen() {
        return SwimmingPoolMen;
    }

    public void setSwimmingPoolMen(boolean swimmingPoolMen) {
        SwimmingPoolMen = swimmingPoolMen;
    }

    public boolean isSwimmingPoolSmall() {
        return SwimmingPoolSmall;
    }

    public void setSwimmingPoolSmall(boolean swimmingPoolSmall) {
        SwimmingPoolSmall = swimmingPoolSmall;
    }

    public boolean isStadium() {
        return Stadium;
    }

    public void setStadium(boolean stadium) {
        Stadium = stadium;
    }

    public boolean isWifi() {
        return Wifi;
    }

    public void setWifi(boolean wifi) {
        Wifi = wifi;
    }

    public boolean isBilliard() {
        return Billiard;
    }

    public void setBilliard(boolean billiard) {
        Billiard = billiard;
    }

    public boolean isKitchen() {
        return Kitchen;
    }

    public void setKitchen(boolean kitchen) {
        Kitchen = kitchen;
    }

    public boolean isGarage() {
        return Garage;
    }

    public void setGarage(boolean garage) {
        Garage = garage;
    }

    public boolean isStereo() {
        return Stereo;
    }

    public void setStereo(boolean stereo) {
        Stereo = stereo;
    }

    public boolean isTennisTable() {
        return TennisTable;
    }

    public void setTennisTable(boolean tennisTable) {
        TennisTable = tennisTable;
    }
}
