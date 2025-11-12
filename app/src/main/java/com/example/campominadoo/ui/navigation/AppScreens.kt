package com.example.campominadoo.ui.navigation

sealed class  AppScreens(val route: String) {
    data object Login : AppScreens("login_screen")

    data object AdminDashboard : AppScreens("admin_dashboard_screen")
    data object AdminEditMode : AppScreens("admin_edit_mode_screen")

    data object PlayerHome : AppScreens("player_home_screen")

    data object Game : AppScreens("game_screen")
}