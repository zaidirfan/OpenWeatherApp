package com.example.openweatherapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
public class Converter {
	public static Weather ConvertToWeather(JSONObject obj){
		Weather info = new Weather();
		try {
			JSONObject temp = obj.getJSONObject("temp");		
			info.setMaxTemp(temp.getString("max"));		
			info.setMinTemp(temp.getString("min"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}
	
	public static List<Weather> ConvertToWeatherForecast(JSONArray array){
		List<Weather> forecast = new ArrayList<Weather>();
		try{
		for (int i=0;i<array.length();i++) {
			Weather weather = ConvertToWeather(array.getJSONObject(i));
			weather.setDateCount(i);
			forecast.add(weather);
		}
		}catch(JSONException ex){
			ex.printStackTrace();
		}
		return forecast;
	}
	
	public static WeatherForecast GetWeatherForecast(String content) {		
		WeatherForecast forecast = new WeatherForecast();
		try{
			Log.d("Weather",content);
			JSONObject obj = new JSONObject(content);
			JSONObject city = obj.getJSONObject("city");
			forecast.setCity(city.getString("name"));
			forecast.setCountry(city.getString("country"));
			JSONArray array = obj.getJSONArray("list");
			forecast.setForecast(ConvertToWeatherForecast(array));
		}catch(JSONException ex){
			ex.printStackTrace();
		}
		return forecast;
	}
}
