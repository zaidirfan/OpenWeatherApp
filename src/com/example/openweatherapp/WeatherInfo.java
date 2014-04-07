package com.example.openweatherapp;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class WeatherInfo extends ActionBarActivity {

	GPSTracker gps;
	private SearchType searchType;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_info);
        final Button GetServerData = (Button) findViewById(R.id.ByCities);
        final Button GetServerLocation =  (Button) findViewById(R.id.ByLocation);
        GetServerData.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	
                // WebServer Request URL
            	searchType = SearchType.Name;
                //String serverURL = "http://androidexample.com/media/webservice/JsonReturn.php";
                 
                // Use AsyncTask execute Method To Prevent ANR Problem
                new LongOperation().execute(searchType);
            }
        });    
        GetServerLocation.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
            	 
            	gps = new GPSTracker(WeatherInfo.this);
            	 
                // check if GPS enabled     
                if(gps.canGetLocation()){
                	searchType = SearchType.Location;
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
                new LongOperation().execute(searchType);
            }
        });   
          
    }
      
	 
 
    // Class with extends AsyncTask class
     
    private class LongOperation  extends AsyncTask<SearchType, Void, Void> {
        
        private ProgressDialog Dialog = new ProgressDialog(WeatherInfo.this);
        private String DisplayText;
        
       
        TextView displayResult = (TextView) findViewById(R.id.displayResult);
        EditText txtCities = (EditText) findViewById(R.id.txtCities);
        WeatherService ser;
         
        protected void onPreExecute() {
            // NOTE: You can call UI Element here.
              
            //Start Progress Dialog (Message)
            
            Dialog.setMessage("Please wait..");
            Dialog.show();
             
            //try{
                // Set Request parameter
                //data +="&" + URLEncoder.encode("data", "UTF-8") + "="+serverText.getText();
                     
           // } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
            //    e.printStackTrace();
           // } 
             
        }
  
        // Call after onPreExecute method
        protected Void doInBackground(SearchType... searchTypes) {
             
            /************ Make Post Call To Web Server ***********/
            //BufferedReader reader=null;
        	SearchType search = searchTypes[0];
                 // Send data 
        	switch (search) {
        	case Name:
        		ser = new WeatherService();
        		//Log.d("Weather",txtCities.getText().toString().trim());
        		if(!txtCities.getText().toString().matches("")){
        			try{
        				List<WeatherForecast> forecast = ser.GetForecastByCities(txtCities.getText().toString());
        				DisplayText = DisplayWeatherList(forecast);
        			}
        			catch(Exception ex){
        				DisplayText = ex.getMessage();
        			}
        		}
        		else{
        			DisplayText = "No city entered";
        		}
                break;
        	case Location:
        		ser = new WeatherService();  
        		try{
        			WeatherForecast curForecast = ser.GetForecastByCoord(gps.getLatitude(),gps.getLongitude());
        			DisplayText = DisplayWeather(curForecast);
        		}
        		catch(Exception ex){
        			DisplayText = ex.getMessage();
        		}
                break;
                default:
                	break;
        	}
                
                return null;
                   
        }
        
        private String DisplayWeatherList(List<WeatherForecast> dataList){
        	String returnStr = ""; 
        	for (WeatherForecast info : dataList) {
        		returnStr+=DisplayWeather(info);
        	}
        	return returnStr;
        }
        
        private String DisplayWeather(WeatherForecast data){
        	String returnStr = ""; 
        	returnStr+="City : "+data.getCity()+"\n";
        	returnStr+="Country : "+data.getCountry()+"\n";
			for (Weather weatherInfo : data.getForecast()) {
				returnStr+="Day : "+(weatherInfo.getDateCount()+1)+"\n";
				returnStr+="Max Temp : "+weatherInfo.getMaxTemp()+"\n";
				returnStr+="Min Temp : "+weatherInfo.getMinTemp()+"\n";
			}
        	return returnStr;
        }
          
        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.
              
            // Close progress dialog
            Dialog.dismiss();
            try{
                    displayResult.setText(DisplayText); 
                 } catch (Exception e) {
           
                     e.printStackTrace();
                 }
   
                  
             }  
    }
}
