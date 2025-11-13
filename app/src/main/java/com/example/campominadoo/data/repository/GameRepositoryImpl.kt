package com.example.campominadoo.data.repository

import android.util.Log
import com.example.campominadoo.data.local.dao.ConfiguracoesDao
import com.example.campominadoo.data.local.dao.RankingDao
import com.example.campominadoo.data.local.model.ConfiguracoesUsuario
import com.example.campominadoo.data.local.model.Ranking
import com.example.campominadoo.data.remote.model.ModoDeDificuldade
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class GameRepositoryImpl(
    private val rankingDao: RankingDao,
    private val configuracoesDao: ConfiguracoesDao,
) : GameRepository {

    override fun getRanking(): Flow<List<Ranking>> {
        return rankingDao.getRanking()
    }

    override fun getSettings(): Flow<ConfiguracoesUsuario?> {
        return configuracoesDao.getSettings()
    }

    override suspend fun saveScore(ranking: Ranking) {
        rankingDao.saveScore(ranking)
    }

    override suspend fun updateSettings(config: ConfiguracoesUsuario) {
        configuracoesDao.updateSettings(config)
    }

    override suspend fun clearRanking() {
        rankingDao.clearRanking()
    }



    private val firestoreDb = Firebase.firestore
    private val modosCollection = firestoreDb.collection("modosDeDificuldade")

    override suspend fun getModosDeDificuldade(): List<ModoDeDificuldade> {
       return try {
           val querySnapshot = modosCollection
               .orderBy("linhas")
               .get()
               .await()
           querySnapshot.documents.mapNotNull { doc ->
               val modo = doc.toObject(ModoDeDificuldade::class.java)
               modo?.copy(id = doc.id)
           }
       } catch (e: Exception) {
           Log.e("GameRepositoryImpl", "Erro ao buscar modos", e)
           listOf(ModoDeDificuldade.FACIL)
       }
    }

    override suspend fun addModo(modo: ModoDeDificuldade) {
        try {
            modosCollection.add(modo).await()
        } catch (e: Exception) {
            Log.e("GameRepositoryImpl", "Erro ao adicionar modo", e)
        }
    }

    override suspend fun updateModo(modo: ModoDeDificuldade) {
        if (modo.id.isEmpty()) return
        try {
            modosCollection.document(modo.id).set(modo).await()
        } catch (e: Exception) {
            Log.e("GameRepositoryImpl", "Erro ao atualizar modo", e)
        }
    }

    override suspend fun deleteModo(id: String) {
        if (id.isEmpty()) return
        try {
            modosCollection.document(id).delete().await()
        } catch (e: Exception) {
            Log.e("GameRepositoryImpl", "Erro ao deletar modo", e)
        }
    }
}