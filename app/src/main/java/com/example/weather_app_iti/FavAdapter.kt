package com.example.weather_app_iti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.weather_app_iti.databinding.FavItemBinding

class FavAdapter(var favList:MutableList<FavouriteCity>,var onClick:(FavouriteCity)->Unit,var onRemoveItem:(FavouriteCity)->Unit) :RecyclerView.Adapter<FavAdapter.FavViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding=FavItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.binding.favourite=favList[position]
        holder.binding.root.setOnClickListener {
            onClick(favList[position])
        }
        holder.binding.removeFav.setOnClickListener {
            onRemoveItem(favList[position])
        }
    }
    class FavViewHolder(var binding:FavItemBinding) : ViewHolder(binding.root){

    }
}