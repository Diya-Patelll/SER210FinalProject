package com.example.ser210finalproject

import android.app.Application
import com.example.ser210finalproject.data.AppContainer
import com.example.ser210finalproject.data.AppDataContainer

// initialize our dependency injection container at the very start
class MarketplaceApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}