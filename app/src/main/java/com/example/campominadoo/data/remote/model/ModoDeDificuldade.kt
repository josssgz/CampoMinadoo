package com.example.campominadoo.data.remote.model

import androidx.room.Entity

@Entity(tableName = "modo_de_dificuldade")
data class ModoDeDificuldade(
    val id: String = "",
    val nome: String = "",
    val linhas: Int = 0,
    val colunas: Int = 0,
    val minas: Int = 0
) {
    constructor() : this("","", 0, 0, 0)
}