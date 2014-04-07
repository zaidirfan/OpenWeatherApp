package com.example.openweatherapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class WeatherService {	
	private WeatherForecast GetWeather(String urlPath){
		//WeatherForecast cityWeatherForecast;
		//Log.d("Weather",city);
		
        String Content ="";
        BufferedReader reader=null;
        String data="";
        String Error = null;
		try
        { 
           
           // Defined URL  where to send data
           URL url = new URL(urlPath);
              
          // Send POST data request

          URLConnection conn = url.openConnection(); 
          conn.setDoOutput(true); 
          OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
          wr.write( data ); 
          wr.flush(); 
       
          // Get the server response 
            
          reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
          StringBuilder sb = new StringBuilder();
          String line = null;
         
            // Read Server Response
            while((line = reader.readLine()) != null)
                {
                       // Append server response in string
                       sb.append(line + "");
                }
             
            // Append Server Response To Content String 
           Content = sb.toString();
        }
        catch(Exception ex)
        {
            Error = ex.getMessage();
        }
        finally
        {
            try
            {
  
                reader.close();
            }

            catch(Exception ex) {}
        }
     
    /*****************************************************/
    return Converter.GetWeatherForecast(Content);

	}
	
	public List<WeatherForecast> GetForecastByCities(String cityNames){
		List<WeatherForecast> collection = new ArrayList<WeatherForecast>();
		String[] cities = cityNames.split(";");
		for(int i=0;i<cities.length;i++){
			WeatherForecast obj = GetForecastByCity(cities[i]);
			collection.add(obj);
		}		
		return collection;
	}
	
	public WeatherForecast GetForecastByCity(String city){
		//Log.d("Weather",city);
		String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q="+city+"&cnt=14&mode=json&APPID=621aa86348b0784faa7ec4d0f1ba8fcc";
		return GetWeather(url);
	}
	
	public WeatherForecast GetForecastByCoord(double lat,double lon){
		
		String url = "http://api.openweathermap.org/data/2.5/forecast/daily?lat="+lat+"&lon="+lon+"&cnt=14&mode=json&APPID=621aa86348b0784faa7ec4d0f1ba8fcc";
		return GetWeather(url);
	}
	
}
