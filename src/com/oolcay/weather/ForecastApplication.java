package com.oolcay.weather;

import android.app.Application;
import com.oolcay.weather.Models.WeatherLocation;

import java.util.List;

public class ForecastApplication extends Application{

  private int mCurrentLocation = -1;
  private List<WeatherLocation> mAllWeatherLocations = null;
  private int mHeight;

  public int getCurrentLocation(){
    return mCurrentLocation;
  }

  public void setCurrentLocation(int id){
    mCurrentLocation = id;
  }

  public List<WeatherLocation> getAllWeatherLocations(){
    return mAllWeatherLocations;
  }

  public void setAllWeatherLocations(List<WeatherLocation> weatherLocations){
    mAllWeatherLocations = weatherLocations;
  }

  public int getHeight() {
    return mHeight;
  }

  public void setHeight(int height) {
    mHeight = height;
  }
}