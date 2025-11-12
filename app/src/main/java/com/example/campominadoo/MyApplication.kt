package com.example.campominadoo

import android.app.Application
import com.example.campominadoo.data.local.AppDatabase
import com.example.campominadoo.data.repository.GameRepository
import com.example.campominadoo.data.repository.GameRepositoryImpl

class MyApplication : Application() {

    private val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }

    val repository: GameRepository by lazy {
        GameRepositoryImpl(
            rankingDao = database.rankingDao(),
            configuracoesDao = database.configuracoesDao()
        )
    }
}