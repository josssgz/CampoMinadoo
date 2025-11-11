package com.example.campominadoo.ui.screens.jogador

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed // Usaremos itemsIndexed para obter o índice (posição)
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campominadoo.data.local.model.Ranking
// CORREÇÃO: Mantenha APENAS o import correto para sua entidade Ranking.
// Se seu modelo estiver em 'data.local.Ranking':
// Se seu modelo estiver em 'data.local.model.Ranking' (como no código original):
// import com.example.campominadoo.data.local.model.Ranking
// (Remova a outra linha que estiver incorreta)

import com.example.campominadoo.ui.viewmodel.GameViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen(
    onNavigateBack: () -> Unit,
    viewModel: GameViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val rankingList = state.rankingList

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Ranking Global") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (rankingList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Nenhum registro no ranking ainda.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    // Cabeçalho da tabela
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Posição", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.5f))
                        Text("Jogador", fontWeight = FontWeight.Bold, modifier = Modifier.weight(2f))
                        Text("Pontuação", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelLarge)
                        Text("Data", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.5f))
                    }
                    Divider()
                }

                // OTIMIZAÇÃO: Usa itemsIndexed em vez de .withIndex().toList() para melhor performance.
                itemsIndexed(rankingList) { index, ranking ->
                    // A variável 'position' é calculada aqui (index + 1) e passada.
                    RankingItem(position = index + 1, ranking = ranking)
                    Divider()
                }
            }
        }
    }
}

@Composable
fun RankingItem(position: Int, ranking: Ranking) {
    // BOAS PRÁTICAS: O SimpleDateFormat deve ser lembrado para evitar recriação
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // A variável 'position' é usada diretamente (ela é um parâmetro da função).
        Text("$position", modifier = Modifier.weight(0.5f), style = MaterialTheme.typography.labelLarge)
        Text(ranking.nomeJogador, modifier = Modifier.weight(2f), maxLines = 1, style = MaterialTheme.typography.labelLarge)
        Text(ranking.pontuacao.toString(), modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelLarge)
        Text(dateFormat.format(Date(ranking.dataRegistro)), modifier = Modifier.weight(1.5f), style = MaterialTheme.typography.labelSmall)
    }
}