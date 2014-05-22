package com.oolcay.weather.Models;

import java.util.List;

public class Weather{

  public final static String CLEAR_DAY = "clear-day";
  public final static String CLEAR_NIGHT = "clear-night";
  public final static String RAIN = "rain";
  public final static String SNOW = "snow";
  public final static String SLEET = "sleet";
  public final static String WIND = "wind";
  public final static String FOG = "fog";
  public final static String CLOUDY = "cloudy";
  public final static String PARTLY_CLOUDY_DAY = "partly-cloudy_day";
  public final static String PARTLY_CLOUDY_NIGHT = "partly-cloudy-night";

  private String mSummary;
  private double mTemperature;
  private double mFeelsLike;
  private double mPrecipProbability;
  private List<Weather> mHourly;
  private List<Weather> mDaily;
  private int mTime;
  private String mIcon;

  public String getSummary() {
    return mSummary;
  }

  public void setSummary(String summary) {
    mSummary = summary;
  }

  public double getTemperature() {
    return mTemperature;
  }

  public void setTemperature(double temperature) {
    mTemperature = temperature;
  }

  public double getPrecipProbability() {
    return mPrecipProbability;
  }

  public void setPrecipProbability(double precipProbability) {
    mPrecipProbability = precipProbability;
  }

  public List<Weather> getHourly() {
    return mHourly;
  }

  public void setHourly(List<Weather> hourly) {
    this.mHourly = hourly;
  }

  public double getFeelsLike() {
    return mFeelsLike;
  }

  public void setFeelsLike(double feelsLike) {
    mFeelsLike = feelsLike;
  }

  public int getTime() {
    return mTime;
  }

  public void setTime(int time) {
    mTime = time;
  }

  public String getIcon() {
    return mIcon;
  }

  public void setIcon(String icon) {
    mIcon = icon;
  }

  public List<Weather> getDaily() {
    return mDaily;
  }

  public void setDaily(List<Weather> daily) {
    mDaily = daily;
  }
}