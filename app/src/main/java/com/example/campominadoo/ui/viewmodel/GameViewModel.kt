package com.example.campominadoo.ui.viewmodel

import Cell
import GameUiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campominadoo.data.local.model.ConfiguracoesUsuario
import com.example.campominadoo.data.local.model.Ranking
import com.example.campominadoo.data.remote.model.ModoDeDificuldade
import com.example.campominadoo.data.repository.GameRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class GameViewModel(
    private val repository: GameRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState

    private var timerJob: Job? = null

    init {
        collectPersistentData()
        loadModosDeDificuldade()
    }

    private fun collectPersistentData() {
        viewModelScope.launch {
            repository.getRanking().collect { rankingList ->
                _uiState.update { it.copy(rankingList = rankingList) }
            }
        }
        viewModelScope.launch {
            repository.getSettings().collect { settings ->
                _uiState.update { it.copy(currentSettings = settings) }
                if (settings == null) {
                    val defaultSettings = ConfiguracoesUsuario(
                        somHabilitado = true,
                        vibracaoHabilitada = true
                    )
                    repository.updateSettings(defaultSettings)
                }
            }
        }
    }

    //

    fun startGame(modo: ModoDeDificuldade) {
        _uiState.update {
            it.copy(
                currentDifficultyMode = modo,
                currentDifficultyName = modo.nome,
                isGameOverDialogVisible = false
            )
        }

        generateBoard(modo.linhas, modo.colunas, modo.minas)
        startTimer()
    }

    fun loadModosDeDificuldade() {
        viewModelScope.launch {
            val modos = repository.getModosDeDificuldade()
            _uiState.update { it.copy(modosDeDificuldade = modos) }

        }
    }

    fun setCurrentDifficulty(modo: ModoDeDificuldade) {
        _uiState.update {
            it.copy(
                currentDifficultyMode = modo,
                currentDifficultyName = modo.nome,
                gameStatus = GameStatus.READY,
                board = emptyList()
            )
        }
    }

    private fun generateBoard(rows: Int, cols: Int, mines: Int) {
        val newBoard = MutableList(rows) { r ->
            MutableList(cols) { c -> Cell(r, c) }
        }

        val minePositions = mutableSetOf<Pair<Int, Int>>()
        while (minePositions.size < mines) {
            val r = Random.nextInt(rows)
            val c = Random.nextInt(cols)
            minePositions.add(Pair(r, c))
        }

        minePositions.forEach { (r, c) ->
            newBoard[r][c] = newBoard[r][c].copy(isMine = true)
        }

        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (!newBoard[r][c].isMine) {
                    val count = countAdjacentMines(newBoard, r, c)
                    newBoard[r][c] = newBoard[r][c].copy(adjacentMines = count)
                }
            }
        }

        _uiState.update {
            it.copy(
                board = newBoard,
                rows = rows,
                cols = cols,
                totalMines = mines,
                remainingFlags = mines,
                timeElapsed = 0,
                gameStatus = GameStatus.PLAYING,
                isGameOverDialogVisible = false
            )
        }
    }

    private fun countAdjacentMines(board: List<List<Cell>>, r: Int, c: Int): Int {
        var count = 0
        for (dr in -1..1) {
            for (dc in -1..1) {
                if (dr == 0 && dc == 0) continue
                val nr = r + dr
                val nc = c + dc
                if (nr in board.indices && nc in board[0].indices && board[nr][nc].isMine) {
                    count++
                }
            }
        }
        return count
    }

    fun onCellClick(r: Int, c: Int) {
        if (_uiState.value.gameStatus != GameStatus.PLAYING) return

        val currentBoard = _uiState.value.board.toMutableList().map { it.toMutableList() }
        val cell = currentBoard[r][c]

        if (cell.isRevealed || cell.isFlagged) return

        if (cell.isMine) {
            revealAllMines(currentBoard as MutableList<MutableList<Cell>>)
            _uiState.update {
                it.copy(gameStatus = GameStatus.LOST, isGameOverDialogVisible = true, board = currentBoard)
            }
            stopTimer()
        } else {
            val newBoard = revealCell(currentBoard, r, c)
            _uiState.update { it.copy(board = newBoard) }
            checkWinCondition(newBoard)
        }
    }

    fun onCellLongClick(r: Int, c: Int) {
        if (_uiState.value.gameStatus != GameStatus.PLAYING) return

        val currentBoard = _uiState.value.board.toMutableList().map { it.toMutableList() }
        val cell = currentBoard[r][c]

        if (cell.isRevealed) return

        val newFlagged = !cell.isFlagged
        val flagChange = if (newFlagged) -1 else 1

        if (newFlagged && _uiState.value.remainingFlags == 0) return

        currentBoard[r][c] = cell.copy(isFlagged = newFlagged)

        _uiState.update {
            it.copy(
                board = currentBoard,
                remainingFlags = it.remainingFlags + flagChange
            )
        }
    }

    private fun revealCell(board: List<MutableList<Cell>>, r: Int, c: Int): List<List<Cell>> {
        val cell = board[r][c]
        if (cell.isRevealed || cell.isMine) return board

        board[r][c] = cell.copy(isRevealed = true, isFlagged = false) // Revela e remove a bandeira, se houver

        if (cell.adjacentMines == 0) {
            for (dr in -1..1) {
                for (dc in -1..1) {
                    val nr = r + dr
                    val nc = c + dc
                    if (nr in board.indices && nc in board[0].indices) {
                        revealCell(board, nr, nc)
                    }
                }
            }
        }
        return board
    }

    private fun revealAllMines(board: MutableList<MutableList<Cell>>) {
        for (r in board.indices) {
            for (c in board[0].indices) {
                if (board[r][c].isMine) {
                    board[r][c] = board[r][c].copy(isRevealed = true)
                }
            }
        }
    }

    private fun checkWinCondition(board: List<List<Cell>>) {
        val nonMineCells = _uiState.value.rows * _uiState.value.cols - _uiState.value.totalMines
        val revealedCount = board.sumOf { row -> row.count { it.isRevealed && !it.isMine } }

        if (revealedCount == nonMineCells) {
            _uiState.update { it.copy(gameStatus = GameStatus.WON, isGameOverDialogVisible = true) }
            stopTimer()
        }
    }

    fun saveScore(playerName: String) {
        viewModelScope.launch {
            val currentScore = calculateScore(_uiState.value.timeElapsed, _uiState.value.currentDifficultyName)

            val ranking = Ranking(
                nomeJogador = playerName,
                pontuacao = currentScore,
                dataRegistro = System.currentTimeMillis()
            )
            repository.saveScore(ranking)
            _uiState.update { it.copy(isGameOverDialogVisible = false) }
        }
    }

    private fun calculateScore(time: Long, difficulty: String): Int {
        val baseScore = when (difficulty) {
            "Médio" -> 500
            "Difícil" -> 1000
            else -> 100
        }
        val timePenalty = TimeUnit.MILLISECONDS.toSeconds(time).toInt()
        return (baseScore - timePenalty).coerceAtLeast(10)
    }

    fun updateSettings(config: ConfiguracoesUsuario) {
        viewModelScope.launch {
            repository.updateSettings(config)
        }
    }

    fun onClearRanking() {
        viewModelScope.launch {
            repository.clearRanking()
        }
    }

    private fun startTimer() {
        stopTimer()
        timerJob = viewModelScope.launch {
            while (_uiState.value.gameStatus == GameStatus.PLAYING) {
                delay(1000L)
                _uiState.update { it.copy(timeElapsed = it.timeElapsed + 1000L) }
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}