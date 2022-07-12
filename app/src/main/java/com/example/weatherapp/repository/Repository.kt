package com.example.weatherapp.repository

import android.location.Address
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.api.APIService
import com.example.weatherapp.db.dao.RecentDao
import com.example.weatherapp.entities.RecentSearch
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.utils.Constants
import com.example.weatherapp.utils.NetworkResult
import javax.inject.Inject

class Repository @Inject constructor(val apiService: APIService,
                                     private val recentDao: RecentDao) {

    private var _recentSearchList = MutableLiveData<List<RecentSearch>>()
        val recentSearchList get() = _recentSearchList

    private var _weatherResult = MutableLiveData<NetworkResult<WeatherResponse>>()
    val weatherResult get() = _weatherResult

   suspend fun getWeather(lat: Double,lon: Double){
         val response =  apiService.getWeather(lat,lon,Constants.weather_key)
       if (response.isSuccessful && response.body() != null) {
           val result = NetworkResult.Success(response.body()!!)
           weatherResult.postValue(result)
       } else {
           weatherResult.postValue(NetworkResult.Error(response.errorBody().toString()))
       }
    }

    suspend fun insertIntoDB(address: Address){
        recentDao.insertRecentSearchData(RecentSearch(address.latitude,address.longitude,address.countryName))
    }

    fun getRecentSearch() {
       val result = recentDao.getRecentSearch()
        _recentSearchList.postValue(result)
    }

}