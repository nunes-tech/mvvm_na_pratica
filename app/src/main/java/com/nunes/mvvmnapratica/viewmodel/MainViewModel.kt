package com.nunes.mvvmnapratica.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nunes.mvvmnapratica.model.Photo
import com.nunes.mvvmnapratica.model.Postagem
import com.nunes.mvvmnapratica.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val mainRepository : MainRepository) : ViewModel() {

    val liveList = MutableLiveData<List<Postagem>>()
    val errorMessage = MutableLiveData<String>()
    val livePhotos = MutableLiveData<List<Photo>>()
    val livePhotosErro = MutableLiveData<String>()

    fun recuperarPostagens() {

        try {
            val request = mainRepository.recuperarPostagens()
            Log.i("mvvm", "recuperarPostagens: chamou a função")
            request.enqueue(
                object : Callback<List<Postagem>> {

                    override fun onResponse(
                        call: Call<List<Postagem>>,
                        response: Response<List<Postagem>>
                    ) {
                        liveList.postValue( response.body() )
                        Log.i("mvvm", "valor passado")
                        Log.i("mvvm", "codigo: ${response.code()}")
                    }

                    override fun onFailure(call: Call<List<Postagem>>, t: Throwable) {
                        errorMessage.postValue(t.message)
                        Log.i("mvvm", "falha")
                    }

                }
            )
        } catch ( e : Exception) {
            Log.i("mvvm", "Erro : ${e.message}")
        }

    }

    fun recuperarPhotos() {

        try {

            val request = mainRepository.recuperarPhotos()

            request.enqueue(
                object : Callback<List<Photo>> {
                    override fun onResponse(
                        call: Call<List<Photo>>,
                        response: Response<List<Photo>>
                    ) {
                        livePhotos.postValue( response.body() )
                    }

                    override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                        livePhotosErro.postValue( "Erro: ${t.message}" )
                    }

                }
            )

        } catch (e : Exception) {
            e.printStackTrace()
        }

    }

}