package com.example.libact

import android.app.Application
import androidx.room.Room

class App: Application() {
    private lateinit var instance:App
    private lateinit var database: AppDatabase
    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "database")
            .build()
    }
    fun getInstance():App{
        return instance
    }
    fun getDB():AppDatabase{
        return database
    }
}