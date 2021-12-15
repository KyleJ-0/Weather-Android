package com.kylerjackson.weather

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.android.volley.RequestQueue
import com.kylerjackson.weather.ui.main.CurrentForecast_Fragment
import com.kylerjackson.weather.ui.main.ExtendedForecast_Fragment
import com.kylerjackson.weather.ui.main.MainViewModel

class TabPagerAdapter(fm : FragmentManager, private var tabCount : Int): FragmentPagerAdapter(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){


    override fun getItem(position : Int):Fragment{
        when(position){
            0->return CurrentForecast_Fragment()
            1->return ExtendedForecast_Fragment()
            else->return CurrentForecast_Fragment()
        }
    }

    override fun getCount():Int{
        return tabCount
    }
}