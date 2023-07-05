package com.nunes.mvvmnapratica.webservice

import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

    val apiJsonPlaceHolder = retrofit2.Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create( IRetrofit::class.java )

}