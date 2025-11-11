package com.example.campominadoo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.campominadoo.data.local.dao.ConfiguracoesDao
import com.example.campominadoo.data.local.dao.RankingDao
// 🟢 CORREÇÃO: Importa o DAO correto
// 🟢 ADIÇÃO: Importa as três entidades que Room deve gerenciar
import com.example.campominadoo.data.local.model.Ranking
import com.example.campominadoo.data.local.model.ConfiguracoesUsuario
import com.example.campominadoo.data.remote.model.ModoDeDificuldade
import com.example.campominadoo.data.local.dao.DificuldadeDao

@Database(
    entities = [Ranking::class, ConfiguracoesUsuario::class, ModoDeDificuldade::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rankingDao(): RankingDao
    abstract fun configuracoesDao(): ConfiguracoesDao
    abstract fun dificuldadeDao(): DificuldadeDao

    // O Jose será responsável pela criação e injeção desta classe (ex: usando um Singleton)
}

