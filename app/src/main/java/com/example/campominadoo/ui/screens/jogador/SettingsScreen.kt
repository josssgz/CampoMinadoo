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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: GameViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // 1. Garantir que o modelo de configurações padrão inclua o ID=1 para Room
    val currentSettings = state.currentSettings ?: ConfiguracoesUsuario(
        id = 1, // <--- CORREÇÃO: Garante que o ID fixo=1 seja usado
        nivelDificuldade = "Fácil",
        somHabilitado = true,
        vibracaoHabilitada = true
    )

    // Estados locais (são usados para controlar o visual dos componentes da UI)
    var selectedDifficulty by remember(currentSettings.nivelDificuldade) {
        mutableStateOf(currentSettings.nivelDificuldade)
    }
    var somHabilitado by remember(currentSettings.somHabilitado) {
        mutableStateOf(currentSettings.somHabilitado)
    }
    var vibracaoHabilitada by remember(currentSettings.vibracaoHabilitada) {
        mutableStateOf(currentSettings.vibracaoHabilitada)
    }

    // Otimização: Não precisamos mais do LaunchedEffect se usarmos remember(key) acima.
    // O estado será inicializado corretamente sempre que currentSettings mudar.

    // Função para salvar as configurações ATUAIS da UI no ViewModel
    val saveSettings: () -> Unit = {
        // 2. CORREÇÃO: Incluir o ID=1 no objeto que será salvo.
        val newSettings = ConfiguracoesUsuario(
            id = 1,
            nivelDificuldade = selectedDifficulty,
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
            // Nível de Dificuldade
            DifficultySetting(
                selected = selectedDifficulty,
                // 3. Melhoria: Ação de salvamento após a atualização do estado local.
                onSelect = {
                    selectedDifficulty = it
                    saveSettings()
                }
            )

            Divider()

            // Opções de Som
            SwitchSetting(
                title = "Som do Jogo",
                description = "Habilita ou desabilita os efeitos sonoros.",
                checked = somHabilitado,
                // 3. Melhoria: Ação de salvamento após a atualização do estado local.
                onCheckedChange = {
                    somHabilitado = it
                    saveSettings()
                }
            )

            Divider()

            // Opções de Vibração
            SwitchSetting(
                title = "Vibração",
                description = "Habilita a vibração em eventos do jogo (ex: mina atingida).",
                checked = vibracaoHabilitada,
                // 3. Melhoria: Ação de salvamento após a atualização do estado local.
                onCheckedChange = {
                    vibracaoHabilitada = it
                    saveSettings()
                }
            )
        }
    }
}

// As funções DifficultySetting e SwitchSetting permanecem inalteradas.

@Composable
fun DifficultySetting(selected: String, onSelect: (String) -> Unit) {
    val difficulties = listOf("Fácil", "Médio", "Difícil")
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Nível de Dificuldade:", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            difficulties.forEach { difficulty ->
                FilterChip(
                    selected = difficulty == selected,
                    onClick = { onSelect(difficulty) },
                    label = { Text(difficulty) },
                    modifier = Modifier.weight(1f)
                )
            }
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