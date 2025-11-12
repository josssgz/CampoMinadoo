package com.example.campominadoo.data.repository

import com.example.campominadoo.data.local.model.ConfiguracoesUsuario
import com.example.campominadoo.data.local.model.Ranking
import com.example.campominadoo.data.remote.model.ModoDeDificuldade
import kotlinx.coroutines.flow.Flow

interface GameRepository{

    fun getRanking(): Flow<List<Ranking>>

    suspend fun saveScore(ranking: Ranking)

    fun getSettings(): Flow<ConfiguracoesUsuario?>

    suspend fun updateSettings(config: ConfiguracoesUsuario)

    suspend fun clearRanking()

    //

    suspend fun getModosDeDificuldade(): List<ModoDeDificuldade>

    suspend fun addModo(modo: ModoDeDificuldade)

    suspend fun updateModo(modo: ModoDeDificuldade)

    suspend fun deleteModo(id: String)

}