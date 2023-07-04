package com.nunes.mvvmnapratica.model

import java.io.Serializable


data class Postagem  (
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
) : Serializable