package com.example.csiapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.csiapp.R
import com.example.csiapp.models.NutriInfoModel
import com.google.android.material.textview.MaterialTextView

class IndividualMealAdapter(val individualMealList:List<NutriInfoModel>):RecyclerView.Adapter<IndividualMealAdapter.IndividualMealAdapter>() {
    class IndividualMealAdapter(view: View):RecyclerView.ViewHolder(view){

        val foodSingleText=view.findViewById<MaterialTextView>(R.id.food_single_text_view)
        val calorieSingleText=view.findViewById<MaterialTextView>(R.id.calories_single_text)
        val proteinSingleText=view.findViewById<MaterialTextView>(R.id.protein_total_single_text)
        val fatSingleText=view.findViewById<MaterialTextView>(R.id.fat_total_single_text)
        val cholSingleText=view.findViewById<MaterialTextView>(R.id.cholesterol_total_single_text)
        val sugarSingleText=view.findViewById<MaterialTextView>(R.id.sugar_total_single_text)
        val carbsSingleText=view.findViewById<MaterialTextView>(R.id.carbs_single_text)
        val servingSingleText=view.findViewById<MaterialTextView>(R.id.serving_single_text)
            fun bind(meal:HashMap<String,Any>){
                    foodSingleText.text=meal["name"].toString()
                    calorieSingleText.text="Calories (in cal): "+meal["calories"].toString()
                    proteinSingleText.text="Protein (in g): "+meal["name"].toString()
                    fatSingleText.text="Total Fat: (in g)"+meal["fat_total_g"].toString()
                    cholSingleText.text="Cholesterol (in mg): "+meal["cholesterol_mg"].toString()
                    sugarSingleText.text="Sugar (in g): "+meal["sugar_g"].toString()
                    carbsSingleText.text="Carbohydrates (in g): "+meal["carbohydrates_total_g"].toString()
                    servingSingleText.text="Serving Size(in g): "+meal["serving_size_g"].toString()
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndividualMealAdapter {
        return IndividualMealAdapter(LayoutInflater.from(parent.context).inflate(R.layout.single_meal_info_item,parent,false))
    }

    override fun onBindViewHolder(holder: IndividualMealAdapter, position: Int) {
       holder.bind(individualMealList[position] as HashMap<String, Any>)
    }

    override fun getItemCount(): Int {
        return individualMealList.size;
    }
}