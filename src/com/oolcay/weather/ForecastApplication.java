package com.oolcay.weather;

import android.app.Application;
import com.oolcay.weather.Models.Location;

import java.util.List;

public class ForecastApplication extends Application{

  private int mCurrentLocation = -1;
  private List<Location> mAllLocations = null;

  public int getCurrentLocation(){
    return mCurrentLocation;
  }

  public void setCurrentLocation(int id){
    mCurrentLocation = id;
  }

  public List<Location> getAllLocations(){
    return mAllLocations;
  }

  public void setAllLocations(List<Location> locations){
    mAllLocations = locations;
  }
}