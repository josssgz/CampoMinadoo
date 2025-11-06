package com.example.campominadoo.ui.viewmodel

import com.example.campominadoo.data.remote.model.ModoDeDificuldade

data class AdminUiState(
    val modosDeDificuldade: List<ModoDeDificuldade> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)