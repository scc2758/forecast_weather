package com.oolcay.weather.Models;

public class Location {

  private int mId;
  private String mName;
  private double mLat;
  private double mLon;

  public Location(){}

  public Location(String name, double lat, double lon){
    mName = name;
    mLat = lat;
    mLon = lon;
  }

  public String getName(){
    return mName;
  }

  public double getLat(){
    return mLat;
  }

  public double getLon(){
    return mLon;
  }

  public void setId(int id){
    mId = id;
  }

  public void setName(String name){
    mName = name;
  }

  public void setLat(double lat){
    mLat = lat;
  }

  public void setLon(double lon){
    mLon = lon;
  }
}
