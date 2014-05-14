package com.oolcay.weather;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class LocationActivity extends ListActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {

  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);
  }

}

