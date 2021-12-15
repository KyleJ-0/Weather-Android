package com.kylerjackson.weather.ui.main

import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kylerjackson.weather.R
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kylerjackson.weather.ExtendedWeatherAdapter
import com.kylerjackson.weather.ItemsViewModel

class ExtendedForecast_Fragment: Fragment() {

    lateinit var recyclerView: RecyclerView
    companion object{
        fun newInstance() = ExtendedForecast_Fragment()
    }

    private lateinit var viewModel: MainViewModel
    var listItems = ArrayList<String>()

    interface OnFragmentInteractionListener{
        fun onFragmentInteraction(uri : Uri)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_extended_forecast_, container, false)
        recyclerView = root.findViewById(R.id.recyclerView)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        val resultObserver = Observer<ArrayList<ArrayList<String>>>{
                result-> updateList(result)
        }

        viewModel.extendedWeatherData.observe(viewLifecycleOwner,resultObserver)


    }

    private fun updateList(result:ArrayList<ArrayList<String>>){
        var data = ArrayList<ItemsViewModel>()

        for(item in result){
            data.add(ItemsViewModel(item.get(0),"Temp: "+item.get(1), "High: "+item.get(2),"Low: "+item.get(3)))
        }

        recyclerView.adapter = ExtendedWeatherAdapter(data)
    }
}