package com.oolcay.weather.Models;

import java.util.List;

public class Weather{

  private String mSummary;
  private double mTemperature;
  private double mPrecipProbability;
  private List<Weather> hourly;

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
    return hourly;
  }

  public void setHourly(List<Weather> hourly) {
    this.hourly = hourly;
  }
}