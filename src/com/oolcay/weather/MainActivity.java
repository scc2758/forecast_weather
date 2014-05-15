package com.oolcay.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.oolcay.weather.Models.Location;
import com.oolcay.weather.Network.Request;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends Activity {

  private ForecastApplication state;
  private DatabaseHandler mDatabaseHandler;
  private double mLat;
  private double mLon;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    state = ((ForecastApplication) getApplicationContext());
    Location location = state.getCurrentLocation();

    mDatabaseHandler = new DatabaseHandler(this);

    ConfigHelper configHelper = new ConfigHelper(this);

    List <Location> locations = mDatabaseHandler.getAllLocations();

    if (locations.size() == 0){

    }else{
      if (location == null){
        state.setCurrentLocation(mDatabaseHandler.getLocation(configHelper.getHomeLocation()));
      }
    }

    mLat = state.getCurrentLocation().getLat();
    mLon = state.getCurrentLocation().getLon();

    Toast.makeText(this, " " + state.getCurrentLocation().getName(), Toast.LENGTH_LONG).show();

    GetWeather getWeather = new GetWeather();
    getWeather.execute();
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