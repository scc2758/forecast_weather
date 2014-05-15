package com.oolcay.weather;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ConfigHelper {

  private final static String HOME_LOCATION = "HOME_LOCATION";

  private SharedPreferences mSettings;
  SharedPreferences.Editor mEditor;

  ConfigHelper(Context context){
    mSettings = PreferenceManager.getDefaultSharedPreferences(context);
    mEditor = mSettings.edit();
  }

  public void saveHomeLocation(int locationId){
    mEditor = mSettings.edit();
    mEditor.putInt(HOME_LOCATION, locationId);
    mEditor.commit();
  }

  public int getHomeLocation(){
    return mSettings.getInt(HOME_LOCATION, -1);
  }

  public void clearConfig(){
    mEditor.clear();
  }
}
