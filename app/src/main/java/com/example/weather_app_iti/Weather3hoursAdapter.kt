package com.example.weather_app_iti

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.weather_app_iti.databinding.List3hoursItemBinding

class Weather3hoursAdapter(var context: Context,var list: MutableList<List3hours>) :
    RecyclerView.Adapter<Weather3hoursAdapter.Weather3hoursViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Weather3hoursViewHolder {
        var binding =
            List3hoursItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Weather3hoursViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: Weather3hoursViewHolder, position: Int) {
        holder.binding.list3hour = list[position]
        Setting.getImage(holder.binding.icon,list[position].icon)

    }
    class Weather3hoursViewHolder(var binding: List3hoursItemBinding) : ViewHolder(binding.root)
}