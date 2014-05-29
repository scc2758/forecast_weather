package com.oolcay.weather.Models;

import java.util.List;

public class Weather{

  //icons
  public final static String WIND = "wind";
  public final static String FOG = "fog";
  public final static String CLOUDY = "cloudy";
  public final static String PARTLY_CLOUDY_DAY = "partly-cloudy_day";
  public final static String PARTLY_CLOUDY_NIGHT = "partly-cloudy-night";
  public final static String CLEAR_DAY = "clear-day";
  public final static String CLEAR_NIGHT = "clear-night";

  //icons and preciptype
  public final static String RAIN = "rain";
  public final static String SNOW = "snow";
  public final static String SLEET = "sleet";

  private String mSummary;
  private int mTime;
  private String mIcon;

  private double mTemperature;
  private double mFeelsLike;
  private double mTemperatureMin;
  private int mTemperatureMinTime;
  private double mTemperatureMax;
  private int mTemperatureMaxTime;

  private double mPrecipProbability;
  private String mPrecipType;

  private List<Weather> mHourly;
  private List<Weather> mDaily;


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

  public double getTemperatureMin() {
    return mTemperatureMin;
  }

  public void setTemperatureMin(double temperatureMin) {
    mTemperatureMin = temperatureMin;
  }

  public int getTemperatureMinTime() {
    return mTemperatureMinTime;
  }

  public void setTemperatureMinTime(int temperatureMinTime) {
    mTemperatureMinTime = temperatureMinTime;
  }

  public double getTemperatureMax() {
    return mTemperatureMax;
  }

  public void setTemperatureMax(double temperatureMax) {
    mTemperatureMax = temperatureMax;
  }

  public int getTemperatureMaxTime() {
    return mTemperatureMaxTime;
  }

  public void setTemperatureMaxTime(int temperatureMaxTime) {
    mTemperatureMaxTime = temperatureMaxTime;
  }

  public String getPrecipType() {
    return mPrecipType;
  }

  public void setPrecipType(String precipType) {
    mPrecipType = precipType;
  }
}