package com.kylerjackson.weather

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import android.util.Log
import com.kylerjackson.weather.ui.main.MainViewModel
import kotlinx.coroutines.processNextEventInCurrentThread
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/*
    IMPORTANT INFO:
    API Key: b105ed79a6337f9829f1e89b3048234c
    Link: https://api.openweathermap.org/data/2.5/weather?q=London&appid=b105ed79a6337f9829f1e89b3048234c

    Extended: https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&exclude=hourly,current,minutely,alerts&appid=b105ed79a6337f9829f1e89b3048234c
 */


class RepositoryModule(requestQueue: RequestQueue, var viewModel: MainViewModel) {
    private var rq: RequestQueue = requestQueue
    var longitude: Double = 0.0
    var latitude: Double = 0.0
    var currentWeatherData: ArrayList<String> = ArrayList()
    var extendedWeatherData: ArrayList<String> = ArrayList()

    fun kelvinToFahrenheit(k:Int): Int{
        return ((k - 273.15)*9/5+32).toInt()
    }


    fun updateWeatherData(city:String){
        var data: ArrayList<String> = ArrayList()
        var url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=b105ed79a6337f9829f1e89b3048234c&units=imperial"

        var jsonObjectRequest = JsonObjectRequest(Request.Method.GET,url,null,{response->
            var main:JSONObject = response.getJSONObject("main")
                var temperature = main.getDouble("temp").toInt().toString()
                var temp_high = main.getDouble("temp_max").toInt().toString()
                var temp_low = main.getDouble("temp_min").toInt().toString()

            var weather: JSONObject = response.getJSONArray("weather").getJSONObject(0)
                var description = weather.getString("description")

            var coord: JSONObject = response.getJSONObject("coord")
                longitude = coord.getDouble("lon")
                latitude = coord.getDouble("lat")
            data.add(temperature+"°F")
            data.add("High: "+temp_high+"°F")
            data.add("Low: "+temp_low+"°F")
            data.add(description)
            data.add(city)

            this.requestExtendedForecastData(longitude,latitude)

            viewModel.currentWeatherData.setValue(data)

        },{error ->

        })
        rq.add(jsonObjectRequest)
    }

    fun requestExtendedForecastData(long:Double,lat:Double){
        var data: ArrayList<ArrayList<String>> = ArrayList()
        var url = "https://api.openweathermap.org/data/2.5/onecall?lat="+lat+"&lon="+long+"&exclude=hourly,current,minutely,alerts&appid=b105ed79a6337f9829f1e89b3048234c&units=imperial"

        var jsonObjectRequest = JsonObjectRequest(Request.Method.GET,url,null,{response->

            var sevenDayForecast: JSONArray= response.getJSONArray("daily")


            for(i in 1 until sevenDayForecast.length()){
                var weather = sevenDayForecast.getJSONObject(i)
                var weatherTemps =  weather.getJSONObject("temp")

                var dt = weather.getLong("dt")
                var day = getDayOfWeek(dt)
                Log.i("dt",getDayOfWeek(dt))

                //Log.i("temps: ",weatherTemps.getDouble("day").toString())
                val day_temp = weatherTemps.getDouble("day").toInt()
                val day_max = weatherTemps.getDouble("max").toInt()
                val day_min = weatherTemps.getDouble("min").toInt()


                var weatherData: ArrayList<String> = ArrayList()
                    weatherData.add(day)
                    weatherData.add(day_temp.toString()+"°F")
                    weatherData.add(day_max.toString()+"°F")
                    weatherData.add(day_min.toString()+"°F")

                data.add(weatherData)

                //Log.i("Day "+i.toString()+": ",sevenDayForecast.getJSONObject(i).toString())
            }

            viewModel.extendedWeatherData.setValue(data)

        },{error ->

        })
        rq.add(jsonObjectRequest)
    }
    fun getDayOfWeek(timestamp: Long): String {
        return SimpleDateFormat("EEEE", Locale.ENGLISH).format(timestamp * 1000)
    }
}
