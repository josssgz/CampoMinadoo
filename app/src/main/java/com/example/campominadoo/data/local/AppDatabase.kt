package com.example.campominadoo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.campominadoo.data.local.dao.ConfiguracoesDao
import com.example.campominadoo.data.local.dao.RankingDao
import com.example.campominadoo.data.local.model.Ranking
import com.example.campominadoo.data.local.model.ConfiguracoesUsuario

@Database(
    entities = [Ranking::class, ConfiguracoesUsuario::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun rankingDao(): RankingDao
    abstract fun configuracoesDao(): ConfiguracoesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "campominado_database" // Dê um nome ao seu banco
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

