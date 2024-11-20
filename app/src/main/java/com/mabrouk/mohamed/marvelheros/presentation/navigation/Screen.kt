package com.mabrouk.mohamed.marvelheros.presentation.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Search : Screen("search")
    data object CharacterDetails : Screen("characters_details")
}