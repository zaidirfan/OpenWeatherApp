package com.example.openweatherapp;

import java.util.List;
public class WeatherForecast {
	private String city;
	private String country;
	private List<Weather> forecast;
	public void setCity(String value){
		this.city = value;
	}
	public String getCity() {
		return this.city;
	}
	
	public void setCountry(String value){
		this.country = value;
	}
	public String getCountry() {
		return this.country;
	}
	
	public void setForecast(List<Weather> value){
		this.forecast = value;
	}
	public List<Weather> getForecast() {
		return this.forecast;
	}
}
