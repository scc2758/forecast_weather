package com.oolcay.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.oolcay.weather.Models.WeatherLocation;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "FORECAST_WEATHER";
  private static final int DATABASE_VERSION = 1;

  private static final String TABLE_LOCATIONS = "locations";
  private static final String KEY_ID = "id";
  private static final String KEY_NAME = "name";
  private static final String KEY_LAT = "lat";
  private static final String KEY_LON = "lon";

  public DatabaseHandler(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
   }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String query = "CREATE TABLE " + TABLE_LOCATIONS + "( "
        + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, "
        + KEY_LAT + " REAL, " + KEY_LON + " REAL" + ")";

    db.execSQL(query);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }

  public void addLocation(WeatherLocation weatherLocation) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(KEY_NAME, weatherLocation.getName());
    values.put(KEY_LAT, weatherLocation.getLat());
    values.put(KEY_LON, weatherLocation.getLon());
    db.insert(TABLE_LOCATIONS, null, values);
    db.close();
  }

  public List<WeatherLocation> getAllLocations(){
    List<WeatherLocation> weatherLocations = new ArrayList<WeatherLocation>();

    String selectQuery = "SELECT  * FROM " + TABLE_LOCATIONS;

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    if (cursor.moveToFirst()) {
      do {
        WeatherLocation weatherLocation = new WeatherLocation();
        weatherLocation.setId(Integer.parseInt(cursor.getString(0)));
        weatherLocation.setName(cursor.getString(1));
        weatherLocation.setLat(Double.parseDouble(cursor.getString(2)));
        weatherLocation.setLon(Double.parseDouble(cursor.getString(3)));
        weatherLocations.add(weatherLocation);
      } while (cursor.moveToNext());
    }
    return weatherLocations;
  }

  WeatherLocation getLocation(int id) {
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = db.query(TABLE_LOCATIONS, new String[] { KEY_ID,
        KEY_NAME, KEY_LAT, KEY_LON }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);

    if (cursor != null)
      cursor.moveToFirst();

    WeatherLocation weatherLocation = new WeatherLocation();
    weatherLocation.setId(Integer.parseInt(cursor.getString(0)));
    weatherLocation.setName(cursor.getString(1));
    weatherLocation.setLat(cursor.getDouble(2));
    weatherLocation.setLon(cursor.getDouble(3));

    return weatherLocation;
  }

}
