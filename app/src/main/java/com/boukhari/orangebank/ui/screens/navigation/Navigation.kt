package com.boukhari.orangebank.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.boukhari.orangebank.ui.screens.detail.RepoDetailScreen
import com.boukhari.orangebank.ui.screens.list.RepoListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.RepoListScreen.route
    ) {
        composable(
            route = Screen.RepoListScreen.route
        ) {
            RepoListScreen(onItemClicked = {id ->
                navController.navigate(Screen.DetailScreen.route + "/$id")
            })
        }

       composable(route = Screen.DetailScreen.route + "/{$REPO_DETAIL_ID_EXTRA}",
            arguments = listOf(
                navArgument(REPO_DETAIL_ID_EXTRA) {
                    type = NavType.IntType
                }
            )
        ) {
            RepoDetailScreen()
        }

    }
}