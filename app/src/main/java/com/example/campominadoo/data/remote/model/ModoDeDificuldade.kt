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
        var FACIL = ModoDeDificuldade(
            id = "easy",
            nome = "Fácil",
            linhas = 20,
            colunas = 20,
            minas = 24
        )
    }
}