package com.example.campominadoo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campominadoo.data.remote.model.ModoDeDificuldade
import com.example.campominadoo.data.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdminViewModel(
    private val repository: GameRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdminUiState())

    val uiState: StateFlow<AdminUiState> = _uiState.asStateFlow()

    init{
        loadModos()
    }

    fun loadModos(){
        viewModelScope.launch{
            _uiState.update { it.copy(isLoading = true) }

            val modos = repository.getModosDeDificuldade()

            _uiState.update{
                it.copy(
                    isLoading = false,
                    modosDeDificuldade = modos
                )
            }
        }
    }

    fun addModo(nome: String, linhas: Int, colunas: Int, minas: Int){
        viewModelScope.launch{
            val novoModo = ModoDeDificuldade(
                nome = nome,
                linhas = linhas,
                colunas = colunas,
                minas = minas
            )
            repository.addModo(novoModo)
            loadModos()
        }
    }

    fun deleteModo(id: String){
        viewModelScope.launch {
            repository.deleteModo(id)
            loadModos()
        }
    }

}