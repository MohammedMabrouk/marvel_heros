package com.mabrouk.mohamed.marvelheros.presentation.screens.charactersList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.mabrouk.mohamed.marvelheros.R
import com.mabrouk.mohamed.marvelheros.domain.data.CharacterItem
import com.mabrouk.mohamed.marvelheros.domain.data.GetCharactersDto
import com.mabrouk.mohamed.marvelheros.presentation.navigation.Screen
import com.mabrouk.mohamed.marvelheros.presentation.utils.network.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CharactersListScreen(
    navController: NavHostController,
    viewModel: CharactersViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentFunction by rememberUpdatedState(newValue = {
        viewModel.getPagedCharacters(true)
    })

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                currentFunction()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    val coroutineScope = rememberCoroutineScope()
    val charactersResult by viewModel.charactersResult.collectAsState()
    val charactersList by viewModel.charactersList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isLastPageReached by viewModel.isLastPageReached.collectAsState()

    CharactersListScreenContent(
        coroutineScope = coroutineScope,
        onSearchClicked = { navController.navigate(Screen.Search.route) },
        charactersResult = charactersResult,
        charactersList = charactersList,
        onItemClick = {},
        isLoading = isLoading,
        isLastPageReached = isLastPageReached,
        loadNextCharacters = { viewModel.getPagedCharacters(it) },
        onResetResult = { viewModel.resetResult() }
    )

}

@Composable
fun CharactersListScreenContent(
    coroutineScope: CoroutineScope,
    onSearchClicked: () -> Unit,
    charactersResult: State<GetCharactersDto>,
    charactersList: List<CharacterItem?>?,
    onItemClick: (Int) -> Unit,
    isLoading: Boolean,
    isLastPageReached: Boolean,
    loadNextCharacters: (Boolean) -> Unit,
    onResetResult: () -> Unit,
) {
    // Error State
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.gray))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.black))
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp)
                        .padding(start = 25.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .height(30.dp)
                            .width(80.dp),
                        painter = painterResource(R.drawable.ic_marvel_logo),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "search",
                    tint = colorResource(id = R.color.red),
                    modifier = Modifier
                        .size(22.dp)
                        .clickable { onSearchClicked() }
                )
            }

            when (charactersResult) {
                is State.Error -> {
                    LaunchedEffect(Unit) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(message = charactersResult.errorMessage)
                        }
                    }
                    onResetResult()
                }

                else -> {}
            }

            CharactersList(
                modifier = Modifier,
                charactersList = charactersList,
                isLoading = isLoading,
                isLastPageReached = isLastPageReached,
                loadNextPage = { loadNextCharacters(false) }) {

            }
        }
    }
}

//@Composable
//@Preview(showBackground = true, apiLevel = 28)
//fun CharactersListScreenPreview() {
//    CharactersListScreenContent({})
//}
