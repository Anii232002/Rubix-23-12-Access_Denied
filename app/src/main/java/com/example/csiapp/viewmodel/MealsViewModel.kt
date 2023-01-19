package com.example.csiapp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.csiapp.models.MealInfoModel
import com.example.csiapp.models.NutriInfoModel
import com.example.delizza.network.MealApi
import kotlinx.coroutines.*
import java.lang.Exception

class MealsViewModel() : ViewModel() {
    //pizza list initialised with mutable live data since the list is dynamic and values could change
    private val _mealList = MutableLiveData<List<NutriInfoModel>>()


    //livedata of is immutable as the list object is retrieved here and not changed
    val mealList: LiveData<List<NutriInfoModel>> = _mealList
    val foodItem=MutableLiveData<String>()



    //method for getting the pizza list with network call handled on separate thread using coroutines
    fun getMeal(foodItem: String){

        viewModelScope.launch {
            try {
                _mealList.value=MealApi.retroFitService.getMeals(foodItem)
                Log.d("Pizza_List","Pizza list retrieved successfully! "+mealList.value!!.size)
            }
            catch (e: Exception){
                Log.d("Exception","Exception occurred in fetching data "+e.message)
            }
        }

    }
    }
