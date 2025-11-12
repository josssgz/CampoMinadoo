package com.example.campominadoo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.campominadoo.data.local.model.Ranking
import kotlinx.coroutines.flow.Flow

@Dao
interface RankingDao {

    @Query("SELECT * FROM ranking_table ORDER BY pontuacao DESC, dataRegistro DESC LIMIT 10")
    fun getRanking(): Flow<List<Ranking>>

    @Insert
    suspend fun saveScore(ranking: Ranking)

    @Query("DELETE FROM ranking_table")
    suspend fun clearRanking()

}