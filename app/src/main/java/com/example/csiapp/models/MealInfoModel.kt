package com.example.csiapp.models

import com.squareup.moshi.Json

data class MealInfoModel(val day: String, val time: String, val calories: Int,val nutriList:List<NutriInfoModel>)

data class  NutriInfoModel(
    @Json(name = "name") val name: String,
    @Json(name = "calories")val calories: Double,
    @Json(name = "serving_size_g")val serving_size_g: Double,
    @Json(name = "fat_total_g")val fat_total_g: Double,
    @Json(name = "fat_saturated_g")val fat_saturated_g: Double,
    @Json(name = "protein_g")val protein_g: Double,
    @Json(name = "cholesterol_mg")val cholesterol_mg: Double,
    @Json(name = "carbohydrates_total_g")val carbohydrates_total_g: Double,
    @Json(name = "sugar_g")val sugar_g: Double
)
