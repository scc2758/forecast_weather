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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    addLocationEditText = (EditText)findViewById(R.id.add_location);

    setupListeners();


    //setListAdapter(adapter);

  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);
  }

  private void findLocation(String location){
    Geocoder geocoder = new Geocoder(this);
    Address address = null;
    List<Address> addresses;
    double latitude = 0;
    double longitude = 0;
    try {
      addresses = geocoder.getFromLocationName(location, 1);
      if(addresses.size() > 0) {
        latitude= addresses.get(0).getLatitude();
        longitude= addresses.get(0).getLongitude();
      }
    } catch (IOException e) {
      Log.e("WEATHER", e.toString());
    }
    Toast.makeText(this, latitude + ", " + longitude, Toast.LENGTH_LONG).show();
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