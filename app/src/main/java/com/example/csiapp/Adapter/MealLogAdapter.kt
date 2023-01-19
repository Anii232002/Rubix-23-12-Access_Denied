package com.example.csiapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.csiapp.R
import com.example.csiapp.models.MealInfoModel

class MealLogAdapter(val mealItems:ArrayList<MealInfoModel>): RecyclerView.Adapter<MealLogAdapter.MealLogViewHolder>() {
    class MealLogViewHolder(view:View):RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealLogViewHolder {
        return MealLogViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.meal_item,parent,false))
    }

    override fun onBindViewHolder(holder: MealLogViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return mealItems.size;
    }
}