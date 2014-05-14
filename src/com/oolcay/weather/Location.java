package com.oolcay.weather;

public class Location {

  private int mId;
  private String mName;
  private float mLat;
  private float mLon;

  Location(String name, float lat, float lon){
    mName = name;
    mLat = lat;
    mLon = lon;
  }

  public String getName(){
    return mName;
  }

  public float getLat(){
    return mLat;
  }

  public float getLon(){
    return mLon;
  }
}
