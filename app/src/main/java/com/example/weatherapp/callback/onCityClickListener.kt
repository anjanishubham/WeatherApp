package com.example.weatherapp.callback

import com.example.weatherapp.entities.RecentSearch

interface onCityClickListener {

    fun onSelectItem(item : RecentSearch)
}