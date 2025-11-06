package com.example.campominadoo.data.repository

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.example.campominadoo.data.local.model.ConfiguracoesUsuario
import com.example.campominadoo.data.local.model.Ranking
import com.example.campominadoo.data.local.dao.ConfiguracoesDao
import com.example.campominadoo.data.local.dao.RankingDao
import com.example.campominadoo.data.remote.model.ModoDeDificuldade
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await


class GameRepositoryImpl(
    private val rankingDao: RankingDao,
    private val configDao: ConfiguracoesDao
) : GameRepository {

    private val firestoreDb = Firebase.firestore
    private val modosCollection = firestoreDb.collection("modosDeDificuldade")

    override fun getRanking(): Flow<List<Ranking>> {
        // logica - (chamar dao)
        return rankingDao.getRanking()
    }

    override suspend fun saveScore(ranking: Ranking) {
        rankingDao.saveScore(ranking)
    }

    override fun getSettings(): Flow<ConfiguracoesUsuario> {
        return configDao.getSettings()
    }

    override suspend fun updateSettings(config: ConfiguracoesUsuario) {
        configDao.updateSettings(config)
    }

    override suspend fun getModosDeDificuldade(): List<ModoDeDificuldade> {
        return try {
            val querySnapshot = modosCollection.get().await()

            querySnapshot.documents.mapNotNull { doc ->
                val modo = doc.toObject(ModoDeDificuldade::class.java)
                modo?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            Log.e("GameRepositoryImpl", "Erro ao buscar modos de dificuldade", e)
            emptyList()
        }
    }

    override suspend fun addModo(modo: ModoDeDificuldade) {
        try{
            modosCollection.add(modo).await()
        }
        catch (e: Exception) {
            Log.e("GameRepositoryImpl", "Erro ao adicionar modo", e)
        }
    }

    override suspend fun updateModo(modo: ModoDeDificuldade) {
        if(modo.id.isEmpty()) {
            Log.e("GameRepositoryImpl", "ID do modo está vazio, não é possível atualizar")
            return
        }
        try {
            modosCollection.document(modo.id).set(modo).await()
        }
        catch (e: Exception) {
            Log.e("GameRepositoryImpl", "Erro ao atualizar modo", e)
        }
    }

    override suspend fun deleteModo(id: String) {
        if(id.isEmpty()) {
            Log.e("GameRepositoryImpl", "ID está vazio, não é possível deletar")
            return
        }
        try{
            modosCollection.document(id).delete().await()
        }
        catch (e: Exception) {
            Log.e("GameRepositoryImpl", "Erro ao deletar modo", e)
        }
    }

}