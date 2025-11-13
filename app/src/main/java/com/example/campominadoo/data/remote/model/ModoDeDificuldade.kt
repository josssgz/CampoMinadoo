package com.example.campominadoo.data.remote.model

data class ModoDeDificuldade(
    val id: String = "",
    val nome: String = "",
    val linhas: Int = 0,
    val colunas: Int = 0,
    val minas: Int = 0
) {
    constructor() : this("","", 0, 0, 0)

    companion object {
        val FACIL = ModoDeDificuldade(
            id = "easy",
            nome = "Fácil",
            linhas = 8,
            colunas = 8,
            minas = 10
        )
        val MEDIO = ModoDeDificuldade(
            id = "medium",
            nome = "Médio",
            linhas = 10,
            colunas = 10,
            minas = 14
        )
    }
}