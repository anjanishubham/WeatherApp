package com.example.weatherapp.repository

import android.util.Log
import com.example.weatherapp.api.APIService
import com.example.weatherapp.utils.Constants
import com.example.weatherapp.utils.NetworkResult
import javax.inject.Inject

class Repository @Inject constructor(val apiService: APIService) {

   suspend fun getWeather(lat: Double, lon:Double){
         val response =  apiService.getWeather(lat,lon,Constants.weather_key)
       if (response.isSuccessful && response.body() != null) {
           val result = NetworkResult.Success(response.body()!!)
           Log.d(Constants.TAG, "getWeather: result" + result.data.toString())
       } else {
           Log.d(Constants.TAG, "getWeather:error ")
       }
    }

}