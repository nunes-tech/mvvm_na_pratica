package com.nunes.mvvmnapratica.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nunes.mvvmnapratica.databinding.PostagemRecyclerBinding
import com.nunes.mvvmnapratica.model.Photo
import com.nunes.mvvmnapratica.model.Postagem
import com.nunes.mvvmnapratica.view.OnItemClickListener
import com.squareup.picasso.Picasso

class AdapterPostagem : RecyclerView.Adapter<AdapterPostagem.PostagemViewHolder>() {

    private var postagens = listOf<Postagem>()
    private var photos = listOf<Photo>()
    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    fun atualizarLista(list : List<Postagem>){
        this.postagens = list
        notifyDataSetChanged()
    }

    fun atualizarPhotos(listPhotos: List<Photo>?) {
       if (listPhotos != null) {
           this.photos = listPhotos
           notifyDataSetChanged()
       }
    }



    inner class PostagemViewHolder (private val bind : PostagemRecyclerBinding)
        : RecyclerView.ViewHolder(bind.root) {

            fun conectarBind(position : Int) {
                bind.textBody.text = postagens[position].body
                bind.textTitulo.text = postagens[position].title

                if (photos.isNotEmpty()) {
                    Picasso.get()
                        .load( photos[position].thumbnailUrl )
                        .into( bind.imageCapa )
                }

            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostagemViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PostagemRecyclerBinding.inflate(layoutInflater, parent, false)
        return PostagemViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return postagens.size
    }

    override fun onBindViewHolder(holder: PostagemViewHolder, position: Int) {
        holder.conectarBind(position)

        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick( postagens[position] )
        }
    }

}