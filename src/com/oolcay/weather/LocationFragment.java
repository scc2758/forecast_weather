package com.oolcay.weather;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.oolcay.weather.Models.Location;
import com.oolcay.weather.Models.Weather;

public class LocationFragment extends Fragment {

  public static final String EXTRA_LOCATION_ID = "extra_location_id";

  private Location mLocation;
  private Context mContext;
  private ForecastApplication mForecastApplication;

  @Override
  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);

    int id = getArguments().getInt(LocationFragment.EXTRA_LOCATION_ID);

    mContext = getActivity();
    mForecastApplication = (ForecastApplication)mContext.getApplicationContext();
    mLocation = mForecastApplication.getAllLocations().get(id);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent,
      Bundle savedInstanceState){
    View v = inflater.inflate(R.layout.location_fragment, parent, false);

    LinearLayout firstPage = (LinearLayout)v.findViewById(R.id.onePage);

    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) firstPage.getLayoutParams();
    params.height = mForecastApplication.getHeight();
    firstPage.setLayoutParams(params);

    TextView textView = (TextView)v.findViewById(R.id.location);
    textView.setText(mLocation.getName());

    Weather weather = mLocation.getWeather();

    textView = (TextView)v.findViewById(R.id.temp);
    textView.setText(" " + Math.round(weather.getTemperature()));

    textView = (TextView)v.findViewById(R.id.summary);
    textView.setText(" " + weather.getSummary());

    GraphView.GraphViewData[] points;
    int length = 24; //weather for the next 24 hours
    points = new GraphView.GraphViewData[length];
    for (int x = 0; x < length; x++){
      int time = weather.getHourly().get(x).getTime();
      double temp = weather.getHourly().get(x).getTemperature();
      points[x] = new GraphView.GraphViewData(time, temp);
    }

    GraphView graphView = new LineGraphView(
        mContext
        , "Hourly Tempature" // heading
    );

    graphView.getGraphViewStyle().setNumHorizontalLabels(1);
    graphView.getGraphViewStyle().setGridColor(Color.TRANSPARENT);
    graphView.getGraphViewStyle().setNumVerticalLabels(5);
    //graphView.setHorizontalLabels(new String[] {"12:01 am", "12:00 pm", "11:59 pm"});
    graphView.addSeries(new GraphViewSeries(points));
    LinearLayout layout = (LinearLayout) v.findViewById(R.id.graph);
    layout.addView(graphView);

    return v;
  }

  public static LocationFragment newInstance(int locationId){
    Bundle args = new Bundle();
    args.putInt(EXTRA_LOCATION_ID, locationId);

    LocationFragment fragment = new LocationFragment();
    fragment.setArguments(args);

    return fragment;
  }
}
