package com.oolcay.weather;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.oolcay.weather.Network.Request;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    GetWeather getWeather = new GetWeather();
    getWeather.execute();
  }

  private void handleResponse(JSONObject results){

    if (results == null){
      Toast.makeText(this, "Error Loading Weather Data", Toast.LENGTH_SHORT);
    }



    GraphView.GraphViewData[] points = new GraphView.GraphViewData[0];

    try {
     JSONObject hourly = results.getJSONObject("hourly");
     JSONArray hourly_data = hourly.getJSONArray("data");

     int length = hourly_data.length();
     points = new GraphView.GraphViewData[length];

      for (int x = 0; x < length; x++){
        JSONObject data_point =  hourly_data.getJSONObject(x);
        int time = data_point.getInt("time");
        double temp = data_point.getDouble("temperature");
        points[x] = new GraphView.GraphViewData(time, temp);
      }
    } catch (JSONException e) {
      Log.e("WEATHER_APP: ", e.toString());
    }

    GraphView graphView = new LineGraphView(
        this // context
        , "GraphViewDemo" // heading
    );

    graphView.getGraphViewStyle().setNumHorizontalLabels(1);
    graphView.getGraphViewStyle().setNumVerticalLabels(5);
    graphView.setHorizontalLabels(new String[] {"12:01 am", "12:00 pm", "11:59 pm"});

    graphView.addSeries(new GraphViewSeries(points));
    LinearLayout layout = (LinearLayout) findViewById(R.id.graph);
    layout.addView(graphView);

  }
  public class GetWeather extends AsyncTask<Object, Void, JSONObject> {

    @Override
    protected JSONObject doInBackground(Object... params) {
      JSONObject weatherData = null;
      try{
        Request request = new Request();
        request.setUrl("https://api.forecast.io/forecast//34.988,-81.237");
        weatherData = request.getJsonResponse();
      }catch (Exception e){
        Log.e("WEATHER", e.toString());
      }
      return weatherData;
    }

    @Override
    protected void onPostExecute(JSONObject result){
      handleResponse(result);
    }
  }
}