package com.kylerjackson.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import android.net.Uri
import android.util.Log
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.kylerjackson.weather.ui.main.CurrentForecast_Fragment
import com.kylerjackson.weather.ui.main.ExtendedForecast_Fragment
import com.kylerjackson.weather.ui.main.MainViewModel
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity(), CurrentForecast_Fragment.OnFragmentInteractionListener, ExtendedForecast_Fragment.OnFragmentInteractionListener {

    private lateinit var requestQueue: RequestQueue
    private lateinit var repositoryModule: RepositoryModule
    private lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        requestQueue = Volley.newRequestQueue(this)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        repositoryModule = RepositoryModule(requestQueue,mainViewModel)
        mainViewModel.addRepo(repositoryModule)

        configureTabLayout()


        val city_view = findViewById(R.id.city_search) as Button
        city_view.setOnClickListener {
            if(!city_input.text.toString().isBlank()) {
                mainViewModel.setCity(city_input.text.toString())
                mainViewModel.updateWeatherData()
            }
        }
    }

    private fun configureTabLayout(){

        tab_layout.addTab(tab_layout.newTab().setText("Current Forecast"))
        tab_layout.addTab(tab_layout.newTab().setText("Extended Forecast"))

        val adapter = TabPagerAdapter(supportFragmentManager, tab_layout.tabCount)
        pager.adapter = adapter
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))

        tab_layout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager.currentItem = tab.position
            }
            override fun onTabUnselected(tab:TabLayout.Tab){
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }

    override fun onFragmentInteraction(uri:Uri){

    }
}