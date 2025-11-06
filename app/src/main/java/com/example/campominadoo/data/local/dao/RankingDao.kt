package com.example.campominadoo.data.local.dao

import com.example.campominadoo.data.local.model.Ranking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface RankingDao {

    // A Crys vai implementar o @Query aqui depois
    fun getRanking(): Flow<List<Ranking>> = flowOf(emptyList()) // Retorna um Flow vazio

    // A Crys vai implementar o @Insert aqui depois
    suspend fun saveScore(ranking: Ranking) {
        // Não faz nada por enquanto
    }
}