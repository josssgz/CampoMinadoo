package com.example.campominadoo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.campominadoo.data.local.model.ConfiguracoesUsuario
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfiguracoesDao {
    @Query("SELECT * FROM configuracoes_table WHERE id = 1")
    fun getSettings(): Flow<ConfiguracoesUsuario?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSettings(config: ConfiguracoesUsuario)

}