package com.oolcay.weather;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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

  private void setupListeners(){
    addLocationEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
          //findLocation(addLocationEditText.getText());
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