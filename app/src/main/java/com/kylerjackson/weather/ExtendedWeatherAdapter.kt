package com.kylerjackson.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExtendedWeatherAdapter(private val dataList: List<ItemsViewModel>): RecyclerView.Adapter<ExtendedWeatherAdapter.ViewHolder>() {
    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(view.context).inflate(R.layout.row_item,view,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val itemsViewModel = dataList[position]

        holder.day.text = itemsViewModel.day
        holder.temp.text = itemsViewModel.temperature
        holder.high.text = itemsViewModel.temp_high
        holder.low.text = itemsViewModel.temp_low


    }

    override fun getItemCount(): Int{
        return dataList.size

    }

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        val day: TextView = itemView.findViewById(R.id.day)
        val temp: TextView = itemView.findViewById(R.id.temp)
        val high: TextView = itemView.findViewById(R.id.high)
        val low: TextView = itemView.findViewById(R.id.low)
    }
}