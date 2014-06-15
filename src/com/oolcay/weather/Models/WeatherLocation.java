package com.oolcay.weather.Models;

import android.location.Location;

public class WeatherLocation extends Location {

  private int mId;
  private String mName;
  private long mLastUpdated;
  private Weather mWeather;

  public Weather getWeather() {
    return mWeather;
  }

  public void setWeather(Weather weather) {
    mWeather = weather;
  }

  public WeatherLocation(){
    super("");
    mLastUpdated = -1;
  }

  public WeatherLocation(String name, double lat, double lon){
    super("");
    mName = name;
    mLastUpdated = 0;
  }

  public long getLastUpdated(){
    return mLastUpdated;
  }

  public String getName(){
    return mName;
  }

  public int getId(){
    return mId;
  }

  public void setLastUpdated(long updated){
    mLastUpdated = updated;
  }

  public void setId(int id){
    mId = id;
  }

  public void setName(String name){
    mName = name;
  }
}
