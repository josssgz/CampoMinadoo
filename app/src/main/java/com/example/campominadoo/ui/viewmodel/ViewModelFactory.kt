package com.example.campominadoo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.campominadoo.MyApplication

class ViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // 1. Pega o repositório da nossa classe Application
        val repository = (application as MyApplication).repository

        // 2. Cria os ViewModels com o repositório
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(repository) as T //
        }

        if (modelClass.isAssignableFrom(AdminViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AdminViewModel(repository) as T //
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}