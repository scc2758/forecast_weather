package com.oolcay.weather;

import android.app.ListActivity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends ListActivity {

  private EditText addLocationEditText;
  private Context mContext;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.locations);
    mContext = this;

    DatabaseHandler databaseHandler = new DatabaseHandler(this);

    //do we need a custom adapter for an array of locations, when all we display is a string
    List<Location> locations = databaseHandler.getAllLocations();
    List<String> locationsArry = new ArrayList<String>();

    for (int x=0; x< locations.size(); x++){
      locationsArry.add(locations.get(0).getName());
      setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, locationsArry ));

    }

    addLocationEditText = (EditText)findViewById(R.id.add_location);

    setupListeners();

  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);
  }

  private void findLocation(String search){
    Geocoder geocoder = new Geocoder(this);
    Address address = null;
    List<Address> addresses;
    Location location = new Location();

    try {
      addresses = geocoder.getFromLocationName(search, 1);
      if(addresses.size() > 0) {
        location.setLat(addresses.get(0).getLatitude());
        location.setLon(addresses.get(0).getLongitude());
        location.setName(addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea());
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        databaseHandler.addLocation(location);
      }
    } catch (IOException e) {
      Log.e("WEATHER", e.toString());
    }
  }
  private void setupListeners(){
    addLocationEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
          findLocation(addLocationEditText.getText().toString());
          InputMethodManager inputManager = (InputMethodManager)
              getSystemService(Context.INPUT_METHOD_SERVICE);
          inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
              InputMethodManager.HIDE_NOT_ALWAYS);
          return true;
        }
        return false;
      }
    });
  }
}