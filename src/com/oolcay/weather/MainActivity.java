package com.oolcay.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.oolcay.weather.Models.WeatherLocation;
import com.google.android.gms.common.GooglePlayServicesUtil;


import java.util.List;

public class MainActivity extends FragmentActivity implements
    GooglePlayServicesClient.ConnectionCallbacks,
    GooglePlayServicesClient.OnConnectionFailedListener {

  private ForecastApplication state;
  private DatabaseHandler mDatabaseHandler;
  private ViewPager mViewPager;
  private LocationClient mLocationClient;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Context context = getApplicationContext();
    state = ((ForecastApplication) getApplicationContext());
    setContentView(R.layout.main);

    int resultCode =
        GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
    // If Google Play services is available
    if (ConnectionResult.SUCCESS == resultCode) {
      Toast.makeText(context, "Play Here", Toast.LENGTH_LONG).show();

      mLocationClient = new LocationClient(this, this, this);
      //WeatherLocation location = mLocationClient.getLastLocation();



    }

    mViewPager = new ViewPager(this);
    mViewPager.setId(R.id.viewPager);

    FrameLayout layout = (FrameLayout) findViewById(R.id.fragmentContainer);
    layout.addView(mViewPager);

    mDatabaseHandler = new DatabaseHandler(context);

    List<WeatherLocation> weatherLocations = mDatabaseHandler.getAllLocations();

    //set weatherLocations or update if count has changed
    if (state.getAllWeatherLocations() == null || weatherLocations.size() != state.getAllWeatherLocations().size()){
      state.setAllWeatherLocations(weatherLocations);
    }

    ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
    progressBar.setVisibility(View.GONE);

    FragmentManager fragmentManager = getSupportFragmentManager();

    mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
      @Override
      public Fragment getItem(int i) {
        return LocationFragment.newInstance(i);

      }
      @Override
      public int getCount() {
        return state.getAllWeatherLocations().size();
      }
    });
  }

  @Override
  public void onResume(){
    super.onResume();
    state = ((ForecastApplication) getApplicationContext());
  }

  @Override
  public void onPause(){
    super.onPause();
    try{
      state.setCurrentLocation(state.getAllWeatherLocations().get(mViewPager.getCurrentItem()).getId());
    }catch (Exception e){

    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_locations:
        openLocations();
        return true;
       default:
        return super.onOptionsItemSelected(item);
    }
  }

  public void openLocations(){
    Intent intent = new Intent(this, LocationActivity.class);
    startActivity(intent);
  }

  private void handleResponse(List<WeatherLocation> results){
    state.setAllWeatherLocations(results);

    ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
    progressBar.setVisibility(View.GONE);

    FragmentManager fragmentManager = getSupportFragmentManager();


    mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
      @Override
      public Fragment getItem(int i) {
        return LocationFragment.newInstance(i);

      }
      @Override
      public int getCount() {
        return state.getAllWeatherLocations().size();
      }
    });

    ConfigHelper configHelper = new ConfigHelper(getApplicationContext());

    int locationId = state.getCurrentLocation();

    if (state.getAllWeatherLocations().size() == 0){

    }else{
      if (locationId == -1){
        state.setCurrentLocation(configHelper.getHomeLocation());
      }
    }

    for (int x=0; x< state.getAllWeatherLocations().size(); x++){
      if (state.getAllWeatherLocations().get(x).getId() == state.getCurrentLocation()){
        mViewPager.setCurrentItem(x);
        break;
      }
    }
  }

  @Override
  public void onConnected(Bundle bundle) {
  }

  @Override
  public void onDisconnected() {
  }

  @Override
  public void onConnectionFailed(ConnectionResult connectionResult) {
  }
}