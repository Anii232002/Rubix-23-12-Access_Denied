package com.example.delizza.network


import com.example.csiapp.models.MealInfoModel
import com.example.csiapp.models.NutriInfoModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

//base url custom rest api
private const val BASE_URL="https://api.api-ninjas.com/v1/"

//initialising the moshi converter factory to be used for parsing the Json response
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//Retrofit initialisation block
private val retroFit=Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

//an interface for all the api specific methods along with the endpoints
interface MealApiService {

    @GET("nutrition")
    @Headers("X-Api-Key:FqjmEYxYiu0DI7AVU38CMQ==aaooxvlz8olCQ9uV")
    suspend fun getMeals(@Query("query")foodItems:String): List<NutriInfoModel>

}

//using singleton pattern to initialise retrofit object only once lazily

object MealApi{
    val retroFitService:MealApiService by lazy {
        retroFit.create(MealApiService::class.java)
    }
}







