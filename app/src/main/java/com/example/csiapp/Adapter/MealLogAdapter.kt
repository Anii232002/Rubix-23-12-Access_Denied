package com.example.csiapp.Adapter

import android.opengl.Visibility
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.csiapp.R
import com.example.csiapp.models.MealInfoModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

class MealLogAdapter(val mealItems:MutableList<MealInfoModel>): RecyclerView.Adapter<MealLogAdapter.MealLogViewHolder>() {
    class MealLogViewHolder(val view:View):RecyclerView.ViewHolder(view){
            val dayText=view.findViewById<MaterialTextView>(R.id.day_logged_text)
            val dateText=view.findViewById<MaterialTextView>(R.id.time_logged)
        val calText=view.findViewById<MaterialTextView>(R.id.calories_intake)
            val layout=view.findViewById<RelativeLayout>(R.id.rl_layout)
            val card=view.findViewById<MaterialCardView>(R.id.meal_single_card)

        val underrv=view.findViewById<RecyclerView>(R.id.single_meal_recycler_view)

            fun bind(meal:MealInfoModel){
                dayText.text=meal.day
                dateText.text=meal.date
                calText.text=meal.total_calories.toString()

                Log.d("INSIDE_RV",meal.nutriList.toString())

                underrv.layoutManager=
                    LinearLayoutManager(view.context,LinearLayoutManager.HORIZONTAL,false)
                underrv.adapter=IndividualMealAdapter(meal.nutriList)

                itemView.setOnClickListener{
                    expand()
                }
            }

        fun expand(){
            var v=0
                if(underrv.visibility==View.GONE)
                    v=View.VISIBLE
                else
                    v=View.INVISIBLE
            TransitionManager.beginDelayedTransition(layout,AutoTransition())

            underrv.visibility=v
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealLogViewHolder {
        return MealLogViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.meal_item,parent,false))
    }

    override fun onBindViewHolder(holder: MealLogViewHolder, position: Int) {

        holder.bind(mealItems[position])
    }

    override fun getItemCount(): Int {
        return mealItems.size;
    }



}