package com.example.campominadoo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.campominadoo.data.remote.model.ModoDeDificuldade

@Dao
interface DificuldadeDao {

    /**
     * Retorna todos os modos de dificuldade salvos, ordenados pelo nome.
     */
    @Query("SELECT * FROM modo_de_dificuldade ORDER BY nome ASC")
    suspend fun getModosDeDificuldade(): List<ModoDeDificuldade>

    /**
     * Adiciona um novo modo de dificuldade.
     * Se o 'id' já existir, ele será substituído.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addModo(modo: ModoDeDificuldade)

    /**
     * Atualiza um modo de dificuldade existente.
     */
    @Update
    suspend fun updateModo(modo: ModoDeDificuldade)

    /**
     * Exclui um modo de dificuldade com base no ID.
     */
    @Query("DELETE FROM modo_de_dificuldade WHERE id = :id")
    suspend fun deleteModo(id: String)
}