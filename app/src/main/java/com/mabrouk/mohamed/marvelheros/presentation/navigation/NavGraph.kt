package com.mabrouk.mohamed.marvelheros.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mabrouk.mohamed.marvelheros.presentation.screens.characterDetails.CharacterDetailsScreen
import com.mabrouk.mohamed.marvelheros.presentation.screens.characterSearch.CharacterSearchScreen
import com.mabrouk.mohamed.marvelheros.presentation.screens.charactersList.CharactersListScreen

@Composable
fun Navigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { CharactersListScreen(navController = navController) }
        composable(Screen.Search.route) { CharacterSearchScreen(navController = navController) }
        composable(
            "${Screen.CharacterDetails.route}?character={character}",
            arguments = listOf(navArgument("character") { type = NavType.StringType })
        ) { navBackStackEntry ->
            CharacterDetailsScreen(navController, navBackStackEntry)
        }
    }
}
