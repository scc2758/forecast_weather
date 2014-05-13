package com.oolcay.weather;

import android.app.Application;

public class ForecastApplication extends Application{
  private int mCurrentLocation;

  public int getCurrentLocation(){
    return mCurrentLocation;
  }

  public void setCurrentLocation(int location){
    mCurrentLocation = location;
  }
}
