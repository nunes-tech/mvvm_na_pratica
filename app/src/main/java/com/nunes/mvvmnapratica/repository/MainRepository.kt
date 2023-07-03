package com.nunes.mvvmnapratica.repository

import com.nunes.mvvmnapratica.webservice.IRetrofit
import com.nunes.mvvmnapratica.webservice.Retrofit

class MainRepository (private val apiService : IRetrofit) {

    fun recuperarPostagens() = apiService.recuperarPostagens()

    fun recuperarPhotos() = apiService.recuperarPhotos()

}