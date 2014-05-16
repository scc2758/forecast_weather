package com.oolcay.weather;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.oolcay.weather.Models.Location;

public class LocationFragment extends Fragment {

  public static final String EXTRA_LOCATION_ID = "extra_location_id";

  private Location mLocation;

  @Override
  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);

    int id = getArguments().getInt(LocationFragment.EXTRA_LOCATION_ID);

    Context context = getActivity();
    DatabaseHandler databaseHandler = new DatabaseHandler(context);
    mLocation = databaseHandler.getLocation(id);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent,
      Bundle savedInstanceState){
    View v = inflater.inflate(R.layout.location_fragment, parent, false);

    TextView textView = (TextView)v.findViewById(R.id.summary);
    textView.setText(mLocation.getName());

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
