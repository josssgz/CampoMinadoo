package com.example.campominadoo.ui.screens.jogador

import Cell
import GameStatus
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.campominadoo.ui.viewmodel.GameViewModel
import com.example.campominadoo.ui.viewmodel.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    factory: ViewModelFactory, onNavigateUp: () -> Unit,
    viewModel: GameViewModel = viewModel(factory = factory)
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.gameStatus) {
        if (state.gameStatus == GameStatus.READY && state.currentDifficultyMode.linhas > 0) {
            viewModel.startGame(state.currentDifficultyMode)
        }
    }

    val allCells = remember(state.board) { state.board.flatten() }

    Scaffold( topBar = {
        CenterAlignedTopAppBar(title = { Text("Jogo Ativo") }) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Bandeiras: ${state.remainingFlags}", fontWeight = FontWeight.Bold)
                Text("Tempo: ${formatTime(state.timeElapsed)}", fontWeight = FontWeight.Bold)
                Text("Status: ${state.gameStatus.name}", fontWeight = FontWeight.Bold)
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(state.cols.coerceAtLeast(1)),
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentPadding = PaddingValues(2.dp)
            ) {
                items(allCells) { cell ->
                    CellComposable(
                        cell = cell,
                        onClick = { viewModel.onCellClick(cell.x, cell.y) },
                        onLongClick = { viewModel.onCellLongClick(cell.x, cell.y) }
                    )
                }
            }
        }
    }

    if (state.isGameOverDialogVisible) {
        GameOverDialog(
            status = state.gameStatus,
            timeElapsed = state.timeElapsed,
            onSaveScore = viewModel::saveScore,
            onDismiss = { /* fazer logica botao fechar */ },
            onNewGame = { viewModel.startGame(state.currentDifficultyMode) }
        )
    }
}

@Composable
fun CellComposable(cell: Cell, onClick: () -> Unit, onLongClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(1.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        color = when {
            cell.isRevealed -> Color.LightGray
            else -> Color(0xFFC0C0C0)
        },
        border = if (!cell.isRevealed) ButtonDefaults.outlinedButtonBorder else null
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = when {
                    cell.isFlagged && !cell.isRevealed -> "🚩"
                    cell.isRevealed && cell.isMine -> "💣"
                    cell.isRevealed && cell.adjacentMines > 0 -> cell.adjacentMines.toString()
                    else -> ""
                },
                color = when (cell.adjacentMines) {
                    1 -> Color.Blue
                    2 -> Color.Green
                    3 -> Color.Red
                    else -> Color.Black
                },
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun formatTime(timeMillis: Long): String {
    val formatter = remember { SimpleDateFormat("mm:ss", Locale.getDefault()) }
    return formatter.format(Date(timeMillis))
}

@Composable
fun GameOverDialog(
    status: GameStatus,
    timeElapsed: Long,
    onSaveScore: (String) -> Unit,
    onDismiss: () -> Unit,
    onNewGame: () -> Unit
) {
    var playerName by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (status == GameStatus.WON) "Vitória! 🎉" else "Game Over 💀") },
        text = {
            Column {
                Text(
                    text = if (status == GameStatus.WON) "Parabéns! Você desarmou todas as bombas em ${formatTime(timeElapsed)}."
                    else "Você atingiu uma mina. Tente novamente!",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                if (status == GameStatus.WON) {
                    OutlinedTextField(
                        value = playerName,
                        onValueChange = { playerName = it },
                        label = { Text("Seu Nome para o Ranking") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            if (status == GameStatus.WON) {
                Button(
                    enabled = playerName.isNotBlank(),
                    onClick = { onSaveScore(playerName) }
                ) {
                    Text("Salvar Pontuação")
                }
            } else {
                Button(onClick = onNewGame) {
                    Text("Novo Jogo")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Fechar")
            }
        }
    )
}