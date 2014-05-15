package com.oolcay.weather;

import android.app.Application;
import com.oolcay.weather.Models.Location;

public class ForecastApplication extends Application{
  private Location mCurrentLocation = null;

  public Location getCurrentLocation(){
    return mCurrentLocation;
  }

  public void setCurrentLocation(Location location){
    mCurrentLocation = location;
  }
}
