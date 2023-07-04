package com.nunes.mvvmnapratica.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nunes.mvvmnapratica.adapter.AdapterPostagem
import com.nunes.mvvmnapratica.databinding.ActivityMainBinding
import com.nunes.mvvmnapratica.model.Photo
import com.nunes.mvvmnapratica.model.Postagem
import com.nunes.mvvmnapratica.repository.MainRepository
import com.nunes.mvvmnapratica.viewmodel.MainViewModel
import com.nunes.mvvmnapratica.viewmodel.MainViewModelFactory
import com.nunes.mvvmnapratica.webservice.Retrofit

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val retrofitInstance by lazy { Retrofit.retrofit }

    private val adapterPostagem by lazy { AdapterPostagem() }

    lateinit var viewModel: MainViewModel

    private lateinit var photo: List<Photo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, MainViewModelFactory( MainRepository( retrofitInstance) ))
            .get(MainViewModel::class.java)

        binding.rvPostagens.adapter = adapterPostagem
        binding.rvPostagens.layoutManager = LinearLayoutManager(this)
        binding.rvPostagens.setHasFixedSize(true)
        /*binding.rvPostagens.addItemDecoration(
            DividerItemDecoration(applicationContext, LinearLayout.VERTICAL)
        )*/

        viewModel.setOnItemClickListener(this)
        adapterPostagem.setOnItemClickListener(this)
    }

    override fun onStart() {
        super.onStart()

        viewModel.liveList.observe(this, Observer {listPostagem ->

            adapterPostagem.atualizarLista( listPostagem )
        })
        
        viewModel.errorMessage.observe(this, Observer {mensagem ->

            Toast.makeText(this, "$mensagem", Toast.LENGTH_SHORT).show()    
            
        })

        viewModel.livePhotos.observe(
            this, Observer {listPhotos ->
                adapterPostagem.atualizarPhotos(listPhotos)
                photo = listPhotos
            }
        )

        viewModel.livePhotosErro.observe(
            this, Observer {mensagemErro ->
                Toast.makeText(
                    applicationContext,
                    "Não foi possivel recuperar as imagens, erro: $mensagemErro",
                    Toast.LENGTH_SHORT).show()
            }
        )

        viewModel.recuperarPostagens()
        viewModel.recuperarPhotos()
    }

    override fun onItemClick(item: Postagem) {

        val intent = Intent(applicationContext, PostagemActivity::class.java)
        intent.putExtra("postagem", item)

        if (photo != null) intent.putExtra("photo", photo[item.id])
        startActivity(intent)

    }
}