package com.oolcay.weather;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.oolcay.weather.Models.Location;
import com.oolcay.weather.Network.Request;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends FragmentActivity {

  private ForecastApplication state;
  private DatabaseHandler mDatabaseHandler;
  private double mLat;
  private double mLon;

  private ViewPager mViewPager;
  private List<Location> mLocations;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Context context = getApplicationContext();

    mViewPager = new ViewPager(this);
    mViewPager.setId(R.id.viewPager);
    setContentView(R.layout.main);

    FrameLayout layout = (FrameLayout) findViewById(R.id.fragmentContainer);
    layout.addView(mViewPager);

    mDatabaseHandler = new DatabaseHandler(context);
    mLocations = mDatabaseHandler.getAllLocations();

    FragmentManager fragmentManager = getSupportFragmentManager();

    state = ((ForecastApplication) getApplicationContext());

    mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
      @Override
      public Fragment getItem(int i) {
        Location location = mLocations.get(i);
          LocationFragment.newInstance(i);
        return LocationFragment.newInstance(location.getId());

      }
      @Override
      public int getCount() {
        return mLocations.size();
      }
    });

    ConfigHelper configHelper = new ConfigHelper(context);

    Location location = state.getCurrentLocation();

    if (mLocations.size() == 0){

    }else{
      if (location == null){
        state.setCurrentLocation(mDatabaseHandler.getLocation(configHelper.getHomeLocation()));
      }
    }

    for (int x=0; x< mLocations.size(); x++){
      if (mLocations.get(x).getId() == state.getCurrentLocation().getId()){
        mViewPager.setCurrentItem(x);
        break;
      }
    }
  }

  @Override
  public void onResume(){
    super.onResume();
  }

  private void handleResponse(JSONObject results){

    if (results == null){
      Toast.makeText(this, "Error Loading Weather Data", Toast.LENGTH_SHORT);
    }

    try {
     JSONObject hourly = results.getJSONObject("hourly");
     JSONArray hourly_data = hourly.getJSONArray("data");

    } catch (JSONException e) {
      Log.e("WEATHER_APP: ", e.toString());
    }
  }

  public void openLocations(View v){
    Intent intent = new Intent(this, LocationActivity.class);
    startActivity(intent);
  }

  public class GetWeather extends AsyncTask<Object, Void, JSONObject> {

    @Override
    protected JSONObject doInBackground(Object... params) {
      JSONObject weatherData = null;
      try{
        Request request = new Request();
        request.setUrl(Constants.FORECAST_URL + Constants.FORECAST_KEY + "/" + mLat + "," + mLon);
        weatherData = request.getJsonResponse();
      }catch (Exception e){
        Log.e("WEATHER", e.toString());
      }
      return weatherData;
    }

    @Override
    protected void onPostExecute(JSONObject result){
      if (result != null)
        handleResponse(result);
    }
  }
}