package com.example.campominadoo.ui.screens.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campominadoo.ui.viewmodel.AdminViewModel
import com.example.campominadoo.ui.viewmodel.ViewModelFactory

@Composable
fun AdminEditModeScreen(
    factory: ViewModelFactory,
    onNavigateUp: () -> Unit
) {
    val viewModel: AdminViewModel = viewModel(factory = factory)

    var nome by remember { mutableStateOf("") }
    var linhas by remember { mutableStateOf("") }
    var colunas by remember { mutableStateOf("") }
    var minas by remember { mutableStateOf("") }

    Scaffold (
        topBar = {
            Text(
                text = "Adicionar Novo Modo",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome (Ex: Médio)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = linhas,
                onValueChange = { linhas = it },
                label = { Text("Nº de linhas") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = colunas,
                onValueChange = { colunas = it },
                label = { Text("Nº de colunas") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = minas,
                onValueChange = { minas = it },
                label = { Text("Nº de minas") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val linhasInt = linhas.toIntOrNull() ?: 0
                    val colunasInt = colunas.toIntOrNull() ?: 0
                    val minasInt = minas.toIntOrNull() ?: 0

                    if(nome.isNotBlank() && linhasInt > 0 && colunasInt > 0 && minasInt > 0) {
                        viewModel.addModo(nome, linhasInt, colunasInt, minasInt)

                        onNavigateUp()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Novo Modo")
            }
        }
    }
}