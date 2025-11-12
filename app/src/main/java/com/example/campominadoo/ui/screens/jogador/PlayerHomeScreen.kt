package com.example.campominadoo.ui.screens.jogador

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.campominadoo.ui.navigation.AppScreens
import com.example.campominadoo.ui.navigation.PlayerBottomBarScreens
import com.example.campominadoo.ui.viewmodel.ViewModelFactory

@Composable
fun PlayerHomeScreen(
    factory: ViewModelFactory,
    // Este é o NavController PRINCIPAL (do AppNavigation)
    mainNavController: NavHostController
) {
    // Este é o NavController INTERNO (só para a Bottom Bar)
    val playerNavController = rememberNavController()

    val screens = listOf(
        PlayerBottomBarScreens.Play,
        PlayerBottomBarScreens.Ranking,
        PlayerBottomBarScreens.Settings,
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by playerNavController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                screens.forEach { screen ->
                    NavigationBarItem(
                        label = { Text(screen.title) },
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            playerNavController.navigate(screen.route) {
                                // Evita empilhar a mesma tela várias vezes
                                popUpTo(playerNavController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        // --- NavHost Aninhado ---
        // Este NavHost controla o CONTEÚDO que aparece acima da Bottom Bar
        NavHost(
            navController = playerNavController,
            startDestination = PlayerBottomBarScreens.Play.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Rota 1: PlayScreen
            composable(PlayerBottomBarScreens.Play.route) {
                PlayScreen(
                    factory = factory,
                    // Quando o PlayScreen quiser ir para o JOGO,
                    // ele usa o NavController PRINCIPAL
                    onNavigateToGame = { mainNavController.navigate(AppScreens.Game.route) }
                )
            }

            // Rota 2: RankingScreen
            composable(PlayerBottomBarScreens.Ranking.route) {
                RankingScreen(
                    factory = factory
                )
            }

            // Rota 3: SettingsScreen
            composable(PlayerBottomBarScreens.Settings.route) {
                SettingsScreen(
                    factory = factory
                )
            }
        }
    }
}