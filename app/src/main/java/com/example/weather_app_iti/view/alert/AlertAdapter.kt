package com.example.weather_app_iti.view.alert

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.weather_app_iti.model.local.Alert
import com.example.weather_app_iti.databinding.AlertItemBinding

class AlertAdapter(var list: MutableList<Alert>, var onClick:((Alert)->Unit)):RecyclerView.Adapter<AlertAdapter.AlertViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val binding=AlertItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AlertViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        holder.binding.alert = list[position]
        holder.binding.removeAlert.setOnClickListener {
            onClick(list[position])
        }
    }



    class AlertViewHolder(var binding: AlertItemBinding):ViewHolder(binding.root){

    }

}