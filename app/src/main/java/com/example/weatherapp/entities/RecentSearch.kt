package com.example.weatherapp.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecentSearch(
    val lat : Double,
    val lon : Double,
    val cityName : String
){
    @PrimaryKey(autoGenerate = true) var id =0
    var recentSearch = true

}