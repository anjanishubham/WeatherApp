package com.example.weatherapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weatherapp.entities.RecentSearch

@Dao
interface RecentDao {

    @Query("SELECT * FROM recentsearch")
    fun getRecentSearch(): List<RecentSearch>

    @Insert
    fun insertRecentSearchData(recentSearch: RecentSearch)

}