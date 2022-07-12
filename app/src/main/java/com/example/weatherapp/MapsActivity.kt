package com.example.weatherapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.callback.onCityClickListener
import com.example.weatherapp.adapter.SearchAdapter
import com.example.weatherapp.databinding.ActivityMapsBinding
import com.example.weatherapp.dialog.WeatherDetailDailog
import com.example.weatherapp.entities.RecentSearch
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.utils.NetworkResult
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(),
    OnMapReadyCallback ,
    onCityClickListener, GoogleMap.OnMarkerClickListener{

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var weatherViewModel : WeatherViewModel
    private lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        weatherViewModel.getRecentSearch()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        observeSearchViewListener()
        searchResult()
    }
    private fun observeSearchViewListener(){
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if(query.isNullOrEmpty().not())
                        searchQuery(query!!)
                return true
            }

        })

        binding.searchView.setOnSearchClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                observerRecentSearch()
            }

        })
    }

    private fun searchQuery(query: String) {
        weatherViewModel.getSearchListResult(query,this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerClickListener(this)
    }

    private fun updateMarker(item: RecentSearch) {
        val latLng = LatLng(item.lat, item.lon)
        mMap.addMarker(MarkerOptions().position(latLng).title(item.cityName))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.0f))
    }


    private fun searchResult(){
        weatherViewModel.searchQueryResult.observe(this, Observer {
            if(it.isNullOrEmpty().not()){
                weatherViewModel.saveIntoDB(it.get(0))
            }
        })
    }

    private fun observerRecentSearch(){
       weatherViewModel.recentSearchList.observe(this, Observer {
            if(it.isNullOrEmpty().not()){
                initAdapter(it)
            }
       })
    }

    private fun initAdapter(list: List<RecentSearch>) {

        adapter = SearchAdapter(list as ArrayList<RecentSearch>,this )
        binding.citySearchResult.visibility = View.VISIBLE
        binding.citySearchResult.adapter = adapter
    }

    override fun onSelectItem(item: RecentSearch) {
        binding.citySearchResult.visibility = View.GONE
        weatherViewModel.getWeather(item.lat,item.lon)
        updateMarker(item)
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        binding.searchView.clearFocus()
        observeWeatherResult()
        return false
    }

   private fun observeWeatherResult(){
        weatherViewModel.weatherResult.observe(this, Observer {
            when(it){
                is NetworkResult.Success -> {
                    showTempDetailDialog(it.data!!)
                }
                is NetworkResult.Error ->{

                }
            }
        })
    }
   private fun showTempDetailDialog(data: WeatherResponse) {
        val temp = (data.main.temp - 273.15).toString()
        val humidity = data.main.humidity.toString()
       val speed = data.wind.speed.toString()
       val weather_condition = data.weather.get(0).description


        WeatherDetailDailog.newInstance(temp,humidity,speed,weather_condition).show(supportFragmentManager, "test")

    }


}