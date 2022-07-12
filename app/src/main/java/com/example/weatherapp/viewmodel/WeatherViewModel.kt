package com.example.weatherapp.viewmodel

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.android.scopes.ViewScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(val repository: Repository): ViewModel() {
    private var _searchQueryResult = MutableLiveData<List<Address>>()
    val searchQueryResult get() = _searchQueryResult


    fun getSearchListResult(query:String,context: Context){
        val geoCoder = Geocoder(context)
       val list = geoCoder.getFromLocationName(query,10)
        searchQueryResult.postValue(list)
    }

    fun getWeather(lat :Double,lon: Double){
        viewModelScope.launch {
            repository.getWeather(lat,lon)
        }
    }

}