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
            Screen.CharacterDetails.route,
            arguments = listOf(navArgument(CHARACTER_ID_ARGUMENT) { type = NavType.IntType })
        ) {
            val characterId = it.arguments?.getInt(CHARACTER_ID_ARGUMENT)
            characterId?.let { id ->
                CharacterDetailsScreen(navController = navController, characterId = id)
            }
        }
    }
}

private const val CHARACTER_ID_ARGUMENT = "character_id"