package com.oolcay.weather.Network;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Request {

  private String mUrl;
  private String mResponse;

  public Request(){
    mUrl =  mResponse = null;
  }

  public Request(String url){
    mUrl = url;
    mResponse = null;
  }

  public void setUrl(String url){
    mUrl = url;
  }

  public JSONObject getJsonResponse() throws IOException{
    doNetworkRequest();
    return new JSONObject();
  }

  public String getStringResponse() throws IOException{
    doNetworkRequest();
    return new String();
  }

  private void doNetworkRequest() throws IOException{
    URL url = new URL(mUrl);
    HttpURLConnection conn =
        (HttpURLConnection) url.openConnection();

    if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
      throw new IOException(conn.getResponseMessage());
    }

    BufferedReader rd = new BufferedReader(
        new InputStreamReader(conn.getInputStream()));
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = rd.readLine()) != null) {
      sb.append(line);
    }
    rd.close();
    conn.disconnect();
    mResponse = sb.toString();
  }
}