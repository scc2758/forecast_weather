package com.oolcay.weather;

public class Location {

  private int mId;
  private String mName;
  private float mLat;
  private float mLon;

  Location(){}

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

  public void setId(int id){
    mId = id;
  }

  public void setName(String name){
    mName = name;
  }

  public void setLat(float lat){
    mLat = lat;
  }

  public void setLon(float lon){
    mLon = lon;
  }
}
