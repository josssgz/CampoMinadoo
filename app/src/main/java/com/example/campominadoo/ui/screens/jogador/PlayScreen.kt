package com.example.campominadoo.ui.screens.jogador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campominadoo.ui.viewmodel.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayScreen(
    onNavigateToGame: () -> Unit,
    onNavigateToRanking: () -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: GameViewModel = viewModel() // Use a factory do Jose aqui
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Campo Minado") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    viewModel.startGame()
                    onNavigateToGame()
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Novo Jogo")
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onNavigateToRanking,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
            ) {
                Text("Ranking")
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onNavigateToSettings,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
            ) {
                Text("Configurações")
            }
        }
    }
}

