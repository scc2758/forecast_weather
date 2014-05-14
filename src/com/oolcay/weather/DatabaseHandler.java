package com.oolcay.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    String query = "CREATE TABLE" + TABLE_LOCATIONS + "()"
        + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, "
        + KEY_LAT + " REAL, " + KEY_LON + " REAL" + ")";

    db.execSQL(query);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }

  public void addLocation(Location location) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(KEY_NAME, location.getName());
    values.put(KEY_LAT, location.getLat());
    values.put(KEY_LON, location.getLon());
    db.insert(TABLE_LOCATIONS, null, values);
    db.close();
  }

  public List<Location> getAllLocations(){
    List<Location> locations = new ArrayList<Location>();

    String selectQuery = "SELECT  * FROM " + TABLE_LOCATIONS;

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    if (cursor.moveToFirst()) {
      do {
        Location location = new Location();
        location.setId(Integer.parseInt(cursor.getString(0)));
        location.setName(cursor.getString(1));
        location.setLat(Float.parseFloat(cursor.getString(2)));
        location.setLon(Float.parseFloat(cursor.getString(3)));
        locations.add(location);
      } while (cursor.moveToNext());
    }
    return locations;
  }
}
