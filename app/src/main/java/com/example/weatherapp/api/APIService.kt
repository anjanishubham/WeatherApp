package com.example.weatherapp.api

import com.example.weatherapp.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("data/2.5/weather")
  suspend  fun getWeather(@Query("lat") lat : Double,
                    @Query("lon") lon: Double,
                    @Query("appid") appid : String): Response<WeatherResponse>
}