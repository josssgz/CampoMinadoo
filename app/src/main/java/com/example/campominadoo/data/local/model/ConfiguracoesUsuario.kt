package com.example.campominadoo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "configuracoes_table")
data class ConfiguracoesUsuario(
    @PrimaryKey
    val id: Int = 1,
    val somHabilitado: Boolean,
    val vibracaoHabilitada: Boolean
)