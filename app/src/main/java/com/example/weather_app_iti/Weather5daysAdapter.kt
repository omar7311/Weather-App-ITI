package com.example.weather_app_iti

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.weather_app_iti.databinding.List5daysItemBinding

class Weather5daysAdapter(var context: Context, var list: MutableList<List5days>) :
    RecyclerView.Adapter<Weather5daysAdapter.Weather5daysViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Weather5daysViewHolder {
        var binding =
            List5daysItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Weather5daysViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Weather5daysViewHolder, position: Int) {
        holder.binding.list5days = list[position]
        Setting.getImage(holder.binding.icon,list[position].icon)

    }

    class Weather5daysViewHolder(var binding: List5daysItemBinding) : ViewHolder(binding.root)

}