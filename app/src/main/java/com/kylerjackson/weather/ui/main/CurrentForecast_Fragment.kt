package com.kylerjackson.weather.ui.main

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kylerjackson.weather.R
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_current_forecast_.*
import androidx.lifecycle.ViewModelProviders

class CurrentForecast_Fragment() : Fragment() {
    companion object{
        fun newInstance() = CurrentForecast_Fragment()
    }

    private lateinit var viewModel: MainViewModel

    interface OnFragmentInteractionListener{
        fun onFragmentInteraction(uri : Uri)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_current_forecast_, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
        viewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)


        val resultObserver = Observer<ArrayList<String>>{
            result->temp_view.text = result.get(0)
                    high_view.text = result.get(1)
                    low_view.text = result.get(2)
                    weather_view.text = result.get(3)
                    city_view.text = result.get(4)
        }
        //viewModel.getCurrentWeatherData().observe(viewLifecycleOwner,resultObserver)

        viewModel.currentWeatherData.observe(viewLifecycleOwner,resultObserver)
    }


}