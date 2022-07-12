package com.example.weatherapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.db.dao.RecentDao
import com.example.weatherapp.entities.RecentSearch

@Database(entities = [RecentSearch::class], version = 1)
abstract class RecentSearchDB : RoomDatabase(){
    abstract fun recentSearchDao(): RecentDao
}