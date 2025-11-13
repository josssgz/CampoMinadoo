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
    mainNavController: NavHostController
) {
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
                                popUpTo(playerNavController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = playerNavController,
            startDestination = PlayerBottomBarScreens.Play.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(PlayerBottomBarScreens.Play.route) {
                PlayScreen(
                    factory = factory,
                    onNavigateToGame = { mainNavController.navigate(AppScreens.Game.route) }
                )
            }

            composable(PlayerBottomBarScreens.Ranking.route) {
                RankingScreen(
                    factory = factory
                )
            }

            composable(PlayerBottomBarScreens.Settings.route) {
                SettingsScreen(
                    factory = factory
                )
            }
        }
    }
}