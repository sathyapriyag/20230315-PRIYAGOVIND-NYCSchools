package com.jpmc.nycschools.model.repository


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitUtil  {

    val BASE_URL = "https://data.cityofnewyork.us/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }


}