package com.example.openweatherapp;

public enum SearchType {
	Name(1),
	Location(2);
	private int value;
	private SearchType(int value){
		this.value = value;
	}
	public int getValue(){
		return this.value;
	}
}
