package com.example.hiveride;

public class Ride {

    private String date, time, startLocation, destination;
    int rideImage;

    public Ride(int rideImage,String date){
        this.rideImage = rideImage;
        this.date = date;
        this.time = time;
        this.startLocation = startLocation;
        this.destination = destination;
    }

    public int getImage(){
        return rideImage;
    }

    public String getDate(){
        return date;
    }

    public String getTime(){
        return time;
    }

    public String getStartLocation(){
        return startLocation;
    }

    public String getDestination(){
        return destination;
    }



}
