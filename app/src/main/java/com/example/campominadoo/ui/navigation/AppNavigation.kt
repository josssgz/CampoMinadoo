package com.example.campominadoo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.campominadoo.ui.screens.LoginScreen
import com.example.campominadoo.ui.screens.admin.AdminDashboardScreen
import com.example.campominadoo.ui.screens.admin.AdminEditModeScreen
import com.example.campominadoo.ui.screens.jogador.GameScreen
import com.example.campominadoo.ui.screens.jogador.PlayerHomeScreen
import com.example.campominadoo.ui.viewmodel.ViewModelFactory

@Composable
fun AppNavigation(factory: ViewModelFactory) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.Login.route
    ) {
        // Tela de Login (Modificada)
        composable(AppScreens.Login.route) {
            LoginScreen(
                // 👇 CORRETO: Navega para a "tela-mãe" do Jogador
                onPlayerClick = { navController.navigate(AppScreens.PlayerHome.route) },
                onAdminClick = { navController.navigate(AppScreens.AdminDashboard.route) }
            )
        }

        // 👇 NOVA ROTA: A "tela-mãe" do Jogador
        composable(AppScreens.PlayerHome.route) {
            PlayerHomeScreen(
                factory = factory,
                mainNavController = navController // Passa o NavController principal
            )
        }

        composable(AppScreens.Game.route) {
            GameScreen(
                factory = factory,
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(AppScreens.AdminDashboard.route) {
            AdminDashboardScreen(
                factory = factory,
                onAddModeClick = { navController.navigate(AppScreens.AdminEditMode.route) }
            )
        }

        composable(AppScreens.AdminEditMode.route) {
            AdminEditModeScreen(
                factory = factory,
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}