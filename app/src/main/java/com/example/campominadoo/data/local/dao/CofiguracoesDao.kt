package com.example.campominadoo.data.local.dao

import com.example.campominadoo.data.local.model.ConfiguracoesUsuario
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface ConfiguracoesDao {

    // A Crys vai implementar o @Query aqui depois
    fun getSettings(): Flow<ConfiguracoesUsuario> = flowOf(ConfiguracoesUsuario())

    // A Crys vai implementar o @Update aqui depois
    suspend fun updateSettings(config: ConfiguracoesUsuario) {
        // Não faz nada por enquanto
    }
}