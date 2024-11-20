package com.mabrouk.mohamed.marvelheros.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.mabrouk.mohamed.marvelheros.presentation.navigation.Navigation
import com.mabrouk.mohamed.marvelheros.presentation.utils.generateMarvelApiHash
import com.mabrouk.mohamed.marvelheros.ui.theme.MarvelHerosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelHerosTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    Navigation(navController = navController)
                }
            }
        }
        val ts = "1"  // Example timestamp
        val publicKey = "2b35723d22cec6ca8b3bd5c34d75e17b"
        val privateKey = "8a247d6981c2f11468dbbba50fc36742b6fc73bf"

        val hash = generateMarvelApiHash(ts, publicKey, privateKey)
        Log.d("www", "onCreate: $hash")
    }
}

// todo: readme file
// colors
// theme / font
// UI
// retrofit