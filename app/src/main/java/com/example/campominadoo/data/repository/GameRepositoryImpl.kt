package com.example.campominadoo.data.repository

import com.example.campominadoo.data.local.dao.ConfiguracoesDao
import com.example.campominadoo.data.local.dao.RankingDao
import com.example.campominadoo.data.local.model.ConfiguracoesUsuario
import com.example.campominadoo.data.local.model.Ranking
import com.example.campominadoo.data.local.dao.DificuldadeDao
import com.example.campominadoo.data.remote.model.ModoDeDificuldade
import kotlinx.coroutines.flow.Flow

class GameRepositoryImpl(
    private val rankingDao: RankingDao,
    private val configuracoesDao: ConfiguracoesDao,
    private val dificuldadeDao: DificuldadeDao
// 🟢 IMPLEMENTAÇÃO CORRETA: O compilador agora sabe o que é GameRepository
) : GameRepository {

    // Implementação das funções de Leitura (Read)
    override fun getRanking(): Flow<List<Ranking>> {
        // A lógica de Room está correta
        return rankingDao.getRanking()
    }

    override fun getSettings(): Flow<ConfiguracoesUsuario?> {
        // A lógica de Room está correta
        return configuracoesDao.getSettings()
    }

    // Implementação das funções de Criação/Atualização (Create/Update)
    override suspend fun saveScore(ranking: Ranking) {
        // A lógica de Room está correta
        rankingDao.saveScore(ranking)
    }

    override suspend fun updateSettings(config: ConfiguracoesUsuario) {
        // A lógica de Room está correta
        configuracoesDao.updateSettings(config)
    }

    override suspend fun getModosDeDificuldade(): List<ModoDeDificuldade> {
        TODO("Not yet implemented")
    }

    override suspend fun addModo(modo: ModoDeDificuldade) {
        TODO("Not yet implemented")
    }

    override suspend fun updateModo(modo: ModoDeDificuldade) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteModo(id: String) {
        TODO("Not yet implemented")
    }
}