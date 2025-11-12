package com.example.campominadoo.ui.screens.jogador

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campominadoo.data.local.model.ConfiguracoesUsuario
import com.example.campominadoo.ui.viewmodel.GameViewModel
import com.example.campominadoo.ui.viewmodel.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    factory: ViewModelFactory,
    viewModel: GameViewModel = viewModel(factory = factory)
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val currentSettings = state.currentSettings ?: ConfiguracoesUsuario(
        id = 1,
        somHabilitado = true,
        vibracaoHabilitada = true
    )

    var somHabilitado by remember(currentSettings.somHabilitado) {
        mutableStateOf(currentSettings.somHabilitado)
    }
    var vibracaoHabilitada by remember(currentSettings.vibracaoHabilitada) {
        mutableStateOf(currentSettings.vibracaoHabilitada)
    }

    val saveSettings: () -> Unit = {
        val newSettings = ConfiguracoesUsuario(
            id = 1,
            somHabilitado = somHabilitado,
            vibracaoHabilitada = vibracaoHabilitada
        )
        viewModel.updateSettings(newSettings)
    }

    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Configurações") }) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SwitchSetting(
                title = "Som do Jogo",
                description = "Habilita ou desabilita os efeitos sonoros.",
                checked = somHabilitado,
                onCheckedChange = {
                    somHabilitado = it
                    saveSettings()
                }
            )

            SwitchSetting(
                title = "Vibração",
                description = "Habilita a vibração em eventos do jogo (ex: mina atingida).",
                checked = vibracaoHabilitada,
                onCheckedChange = {
                    vibracaoHabilitada = it
                    saveSettings()
                }
            )
        }
    }
}

@Composable
fun SwitchSetting(title: String, description: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Spacer(Modifier.width(16.dp))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}