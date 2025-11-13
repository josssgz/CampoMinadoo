package com.example.campominadoo.ui.screens.jogador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campominadoo.ui.viewmodel.GameViewModel
import com.example.campominadoo.ui.viewmodel.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayScreen(
    factory: ViewModelFactory,
    onNavigateToGame: () -> Unit,
    viewModel: GameViewModel = viewModel(factory = factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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

            Text("Escolha a Dificuldade:", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))

            if (uiState.modosDeDificuldade.isEmpty()) {
                Text("Nenhum modo de dificuldade encontrado.")
            }
            else {
                uiState.modosDeDificuldade.forEach { modo ->
                    Button(
                        onClick = {
                            viewModel.startGame(modo)
                            onNavigateToGame()
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text(modo.nome)
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }

        }
    }
}
