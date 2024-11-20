package com.mabrouk.mohamed.marvelheros.presentation.screens.characterSearch

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun CharacterSearchScreen(
    navController: NavHostController,
) {
    CharacterSearchScreenContent()
}


@Composable
fun CharacterSearchScreenContent() {
    Text(text = "CharacterSearchScreen", modifier = Modifier.padding(20.dp), color = Color.Red)
}

@Composable
@Preview(showBackground = true)
fun CharacterSearchScreenPreview() {
    CharacterSearchScreenContent()
}