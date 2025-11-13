import com.example.campominadoo.data.local.model.ConfiguracoesUsuario
import com.example.campominadoo.data.local.model.Ranking
import com.example.campominadoo.data.remote.model.ModoDeDificuldade

/**
 * Representa uma única célula no tabuleiro.
 */
data class Cell(
    val x: Int,
    val y: Int,
    val isMine: Boolean = false,
    val isRevealed: Boolean = false,
    val isFlagged: Boolean = false,
    val adjacentMines: Int = 0 // Número de minas vizinhas
)

/**
 * Estado que a UI observa. Contém TUDO o que a UI precisa saber.
 */
data class GameUiState(
    val board: List<List<Cell>> = emptyList(),

    val currentDifficultyMode: ModoDeDificuldade = ModoDeDificuldade.FACIL,
    val rows: Int = currentDifficultyMode.linhas,
    val cols: Int = currentDifficultyMode.colunas,
    val totalMines: Int = currentDifficultyMode.minas,

    val gameStatus: GameStatus = GameStatus.READY,
    val remainingFlags: Int = totalMines,
    val timeElapsed: Long = 0,
    val rankingList: List<Ranking> = emptyList(),
    val currentSettings: ConfiguracoesUsuario? = null,
    val isGameOverDialogVisible: Boolean = false,
    val modosDeDificuldade: List<ModoDeDificuldade> = emptyList(),
    val currentDifficultyName: String = currentDifficultyMode.nome,

)

enum class GameStatus { READY, PLAYING, WON, LOST }