package com.nunes.mvvmnapratica.webservice

import com.nunes.mvvmnapratica.model.Photo
import com.nunes.mvvmnapratica.model.Postagem
import retrofit2.Call
import retrofit2.http.GET

interface IRetrofit {

    @GET("posts")
    fun recuperarPostagens() : Call<List<Postagem>>

    @GET("photos")
    fun recuperarPhotos() : Call<List<Photo>>

}