package com.kylerjackson.weather.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.kylerjackson.weather.RepositoryModule
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_current_forecast_.*
import kotlinx.android.synthetic.main.fragment_extended_forecast_.*

class MainViewModel(): ViewModel() {
    private lateinit var repository: RepositoryModule
    private var city: String = "Novi"

    val currentWeatherData:MutableLiveData<ArrayList<String>> by lazy {
        MutableLiveData<ArrayList<String>>()
    }

    val extendedWeatherData:MutableLiveData<ArrayList<ArrayList<String>>> by lazy {
        MutableLiveData<ArrayList<ArrayList<String>>>()
    }

    fun addRepo(repo: RepositoryModule) {
        this.repository = repo
    }

    fun updateWeatherData(){
        this.repository.updateWeatherData(city)
    }

    fun setCity(city: String){
        this.city = city
    }


}