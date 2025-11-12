package com.example.campominadoo.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class PlayerBottomBarScreens(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Play : PlayerBottomBarScreens(
        route = "player_play",
        title = "Jogar",
        icon = Icons.Default.PlayArrow
    )
    data object Ranking : PlayerBottomBarScreens(
        route = "player_ranking",
        title = "Ranking",
        icon = Icons.Default.Star
    )
    data object Settings : PlayerBottomBarScreens(
        route = "player_settings",
        title = "Opções",
        icon = Icons.Default.Settings
    )
}