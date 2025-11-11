package com.example.campominadoo.ui.screens.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campominadoo.data.remote.model.ModoDeDificuldade
import com.example.campominadoo.ui.viewmodel.AdminViewModel
import com.example.campominadoo.ui.viewmodel.ViewModelFactory

@Composable
fun AdminDashboardScreen(
    factory: ViewModelFactory,
    onAddModeClick: () -> Unit
) {
    val viewModel: AdminViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsState()

    Scaffold (
        topBar = {
            Text(
                text = "Painel do Admin",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
        },
        floatingActionButton = {
            Button(onClick = onAddModeClick) {
                Text("Adicionar Modo")
            }
        }
    ) { paddingValues ->

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
        ) {
            if(uiState.isLoading){
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else{
                LazyColumn (
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.modosDeDificuldade) { modo ->
                        ModoItemCard(
                            modo = modo,
                            onDelete = { viewModel.deleteModo(modo.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ModoItemCard(
    modo: ModoDeDificuldade,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = modo.nome,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${modo.linhas}x${modo.colunas}, ${modo.minas} minas",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            OutlinedButton(
                onClick = onDelete,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
            ) {
                Text("Excluir")
            }
        }
    }
}
