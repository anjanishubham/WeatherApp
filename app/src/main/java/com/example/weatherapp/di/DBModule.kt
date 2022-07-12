package com.example.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.db.dao.RecentDao
import com.example.weatherapp.db.RecentSearchDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DBModule {

    @Singleton
    @Provides
    fun providesRecentSearchDao(recentSearchDB: RecentSearchDB): RecentDao {
        return recentSearchDB.recentSearchDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): RecentSearchDB {
        return Room.databaseBuilder(
            appContext,
            RecentSearchDB::class.java,
            "_recentDb"
        ).build()
    }
}