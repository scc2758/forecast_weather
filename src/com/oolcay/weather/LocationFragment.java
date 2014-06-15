package com.oolcay.weather;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.oolcay.weather.Models.WeatherLocation;
import com.oolcay.weather.Models.Weather;
import com.oolcay.weather.Network.Request;
import org.json.JSONException;
import org.json.JSONObject;

public class LocationFragment extends Fragment {

  public static final String EXTRA_LOCATION_ID = "extra_location_id";
  public static final int TIME_BETWEEN_UPDATES = 600000; //ten minutes (milli)

  private WeatherLocation mWeatherLocation;
  private Context mContext;
  private ForecastApplication mForecastApplication;
  private Weather mWeather;
  private View mView;
  private int mId;

  @Override
  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);

    mId = getArguments().getInt(LocationFragment.EXTRA_LOCATION_ID);

    mContext = getActivity();
    mForecastApplication = (ForecastApplication)mContext.getApplicationContext();
    mWeatherLocation = mForecastApplication.getAllWeatherLocations().get(mId);
    mWeather = mWeatherLocation.getWeather();

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent,
      Bundle savedInstanceState){

    mView = inflater.inflate(R.layout.location_fragment, parent, false);

    TextView textView = (TextView)mView.findViewById(R.id.weatherLocation);
    textView.setText(mWeatherLocation.getName());

    mForecastApplication = (ForecastApplication)mContext.getApplicationContext();
    mWeatherLocation = mForecastApplication.getAllWeatherLocations().get(mId);

    if (mWeather != null &&  System.currentTimeMillis() - mWeatherLocation.getLastUpdated() < TIME_BETWEEN_UPDATES ){
      displayWeather();
    } else {
      GetWeather getWeather = new GetWeather(mView);
      getWeather.execute();
    }

    return mView;
  }

  public static LocationFragment newInstance(int locationId){
    Bundle args = new Bundle();
    args.putInt(EXTRA_LOCATION_ID, locationId);

    LocationFragment fragment = new LocationFragment();
    fragment.setArguments(args);

    return fragment;
  }

  private void displayWeather(){
    TextView textView;
    mWeather = mWeatherLocation.getWeather();

    showWeather(true);

    textView = (TextView)mView.findViewById(R.id.temp);
    textView.setText(Integer.toString((int)Math.round(mWeather.getTemperature())) + (char) 0x00B0);

    textView = (TextView)mView.findViewById(R.id.summary);
    textView.setText(mWeather.getSummary());

    ImageView imageView = (ImageView)mView.findViewById(R.id.weather_icon);
    //use regex to change icon name to one usable by android
    String resourceId = mWeather.getIcon().replaceAll("-", "_");
    imageView.setImageResource(getResources().getIdentifier(resourceId, "drawable", mContext.getPackageName()));

  }

  private void showWeather(Boolean show){
    LinearLayout weatherHolder = (LinearLayout)mView.findViewById(R.id.weatherHolder);
    ProgressBar progressBar = (ProgressBar)mView.findViewById(R.id.progressBar);

    if (show){
      weatherHolder.setVisibility(View.VISIBLE);
      progressBar.setVisibility(View.GONE);
    }else{
      weatherHolder.setVisibility(View.GONE);
      progressBar.setVisibility(View.VISIBLE);
    }
  }

  public class GetWeather extends AsyncTask<Object, Void, Void> {

    private View mView;

    GetWeather(View v) {
      mView = v;
    }

    @Override
    protected Void doInBackground(Object... params) {
      JSONObject weatherData = null;
      try {
        Request request = new Request();
        request.setUrl(Constants.FORECAST_URL + Constants.FORECAST_KEY + "/" + mWeatherLocation.getLatitude() + "," + mWeatherLocation.getLongitude());
        weatherData = request.getJsonResponse();

        JSONObject currently = weatherData.getJSONObject("currently");
        String temperature = currently.getString("temperature");

        Weather weather = new Weather();

        weather.setTemperature(Double.parseDouble(temperature));
        weather.setSummary(currently.getString("summary"));
        weather.setIcon(currently.getString("icon"));

        mWeatherLocation.setWeather(weather);
        mWeatherLocation.setLastUpdated(System.currentTimeMillis());

        Log.e("LOADING:", "LOADING");

        //mWeatherLocation.setWeather(createWeather(weatherData));

      } catch (Exception e) {
        Log.e("WEATHER", e.toString());
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        displayWeather();
    }

    private Weather createWeather(JSONObject dataPoint) throws JSONException{
      Weather weather = new Weather();

      try {
        weather.setTemperature(dataPoint.getDouble("temperature"));
      } catch (Exception e){
        Log.d(getString(R.string.error_tag), getString(R.string.no_data_in_json));
      }

      try {
        weather.setIcon(dataPoint.getString("icon"));
      } catch (Exception e){
        Log.d(getString(R.string.error_tag), getString(R.string.no_data_in_json));
      }

      weather.setTime(dataPoint.getInt("time"));
      return weather;
    }
  }
}