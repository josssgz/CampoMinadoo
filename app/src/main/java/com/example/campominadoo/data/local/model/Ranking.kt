package com.example.campominadoo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ranking_table")
data class Ranking(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nomeJogador: String,
    val pontuacao: Int,
    val dataRegistro: Long
)