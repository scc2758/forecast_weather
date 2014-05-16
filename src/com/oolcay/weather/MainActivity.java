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

public class MainActivity extends FragmentActivity {

  private ForecastApplication state;
  private DatabaseHandler mDatabaseHandler;
  private double mLat;
  private double mLon;

  private ViewPager mViewPager;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Context context = getApplicationContext();
    state = ((ForecastApplication) getApplicationContext());

    mViewPager = new ViewPager(this);
    mViewPager.setId(R.id.viewPager);
    setContentView(R.layout.main);

    FrameLayout layout = (FrameLayout) findViewById(R.id.fragmentContainer);
    layout.addView(mViewPager);

    mDatabaseHandler = new DatabaseHandler(context);
    state.setAllLocations(mDatabaseHandler.getAllLocations());

    FragmentManager fragmentManager = getSupportFragmentManager();

    mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
      @Override
      public Fragment getItem(int i) {
        return LocationFragment.newInstance(i);

      }
      @Override
      public int getCount() {
        return state.getAllLocations().size();
      }
    });

    ConfigHelper configHelper = new ConfigHelper(context);

    int locationId = state.getCurrentLocation();

    if (state.getAllLocations().size() == 0){

    }else{
      if (locationId == -1){
        state.setCurrentLocation(configHelper.getHomeLocation());
      }
    }

    for (int x=0; x< state.getAllLocations().size(); x++){
      if (state.getAllLocations().get(x).getId() == state.getCurrentLocation()){
        mViewPager.setCurrentItem(x);
        break;
      }
    }
  }

  @Override
  public void onResume(){
    super.onResume();
    state = ((ForecastApplication) getApplicationContext());
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