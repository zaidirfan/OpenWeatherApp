package com.example.openweatherapp;

public class Weather {
	private String maxTemp;
	private String minTemp;
	private int dateCount;
	public void setMaxTemp(String value){
		this.maxTemp = value;
	}
	public String getMaxTemp() {
		return this.maxTemp;
	}
	
	public void setMinTemp(String value){
		this.minTemp = value;
	}
	public String getMinTemp() {
		return this.minTemp;
	}
	
	public void setDateCount(int value){
		this.dateCount = value;
	}
	public int getDateCount() {
		return this.dateCount;
	}
}