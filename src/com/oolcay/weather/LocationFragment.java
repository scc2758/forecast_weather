package com.oolcay.weather;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.oolcay.weather.Models.Location;
import com.oolcay.weather.Network.Request;
import org.json.JSONException;
import org.json.JSONObject;

public class LocationFragment extends Fragment {

  public static final String EXTRA_LOCATION_ID = "extra_location_id";

  private Location mLocation;
  private Context mContext;

  @Override
  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);

    int id = getArguments().getInt(LocationFragment.EXTRA_LOCATION_ID);

    mContext = getActivity();

    ForecastApplication state = (ForecastApplication)mContext.getApplicationContext();

    mLocation = state.getAllLocations().get(id);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent,
      Bundle savedInstanceState){
    View v = inflater.inflate(R.layout.location_fragment, parent, false);

    TextView textView = (TextView)v.findViewById(R.id.summary);
    textView.setText(mLocation.getName());

    GetWeather getWeather = new GetWeather(v);
    getWeather.execute();

    return v;
  }

  public static LocationFragment newInstance(int locationId){
    Bundle args = new Bundle();
    args.putInt(EXTRA_LOCATION_ID, locationId);

    LocationFragment fragment = new LocationFragment();
    fragment.setArguments(args);

    return fragment;
  }

  private void handleResponse(JSONObject results, View v ){

    if (results == null){
      Toast.makeText(mContext, "Error Loading Weather Data", Toast.LENGTH_SHORT);
    }

    try {
      JSONObject hourly = results.getJSONObject("currently");
      String temperature = hourly.getString("temperature");

      TextView textView = (TextView)v.findViewById(R.id.summary);

      textView.setText(temperature);

    } catch (JSONException e) {
      Log.e("WEATHER_APP: ", e.toString());
    }
  }

  public class GetWeather extends AsyncTask<Object, Void, JSONObject> {

    private View mView;

    GetWeather(View v){
      mView = v;
    }

    @Override
    protected JSONObject doInBackground(Object... params) {
      JSONObject weatherData = null;
      try{
        Request request = new Request();
        request.setUrl(Constants.FORECAST_URL + Constants.FORECAST_KEY + "/" + mLocation.getLat() + "," + mLocation.getLon());
        weatherData = request.getJsonResponse();
      }catch (Exception e){
        Log.e("WEATHER", e.toString());
      }
      return weatherData;
    }

    @Override
    protected void onPostExecute(JSONObject result){
      if (result != null)
        handleResponse(result, mView);
    }
  }
}
