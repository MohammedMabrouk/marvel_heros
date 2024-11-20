package com.mabrouk.mohamed.marvelheros.presentation.screens.characterDetails

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.gson.Gson
import com.mabrouk.mohamed.marvelheros.R
import com.mabrouk.mohamed.marvelheros.domain.data.CharacterItem
import com.mabrouk.mohamed.marvelheros.presentation.component.ExternalLinkText
import com.mabrouk.mohamed.marvelheros.presentation.component.SectionContent
import com.mabrouk.mohamed.marvelheros.presentation.component.SectionTitle

@Composable
fun CharacterDetailsScreen(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentFunction by rememberUpdatedState(newValue = {
        val characterJson = navBackStackEntry.arguments?.getString("character")
        val character = Gson().fromJson(Uri.decode(characterJson), CharacterItem::class.java)
        viewModel.setCharacter(character)
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

    val character by viewModel.character.collectAsState()

    CharacterDetailsScreenContent(
        onBackClicked = { navController.popBackStack() },
        character = character
    )
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterDetailsScreenContent(
    onBackClicked: () -> Unit,
    character: CharacterItem?,
) {

    // Error State
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        if (character != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.gray))
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .background(colorResource(id = R.color.black)),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (character.thumbnailUrl.isNotEmpty()) {
                            GlideImage(
                                model = Uri.parse(character.thumbnailUrl),
                                contentDescription = "",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center
                            )
                        } else {
                            Image(
                                painter = painterResource(R.drawable.ic_no_image),
                                contentDescription = "",
                                colorFilter = ColorFilter.tint(colorResource(id = R.color.white)),
                                modifier = Modifier.size(100.dp),
                                alignment = Alignment.Center
                            )
                        }
                    }
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = "search",
                        tint = colorResource(id = R.color.white),
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .padding(top = 18.dp)
                            .size(25.dp)
                            .clickable { onBackClicked() }
                    )
                }

//            when (charactersResult) {
//                is State.Error -> {
//                    LaunchedEffect(Unit) {
//                        coroutineScope.launch {
//                            snackbarHostState.showSnackbar(message = charactersResult.errorMessage)
//                        }
//                    }
//                    onResetResult()
//                }
//
//                else -> {}
//            }
//
//            CharactersList(
//                modifier = Modifier,
//                charactersList = charactersList,
//                isLoading = isLoading,
//                isLastPageReached = isLastPageReached,
//                loadNextPage = { loadNextCharacters(false) }) {
//
//            }

                // name
                if (character.name.isNotEmpty()) {
                    SectionTitle(stringResource(id = R.string.name_lbl))
                    SectionContent(character.name)
                }

                // description
                if (!character.description.isNullOrEmpty()) {
                    SectionTitle(stringResource(id = R.string.description_lbl))
                    SectionContent(character.description)
                }

                // comics
                SectionTitle(stringResource(id = R.string.comics_lbl))
                SectionContent("Hello Worlds")

                // series
                SectionTitle(stringResource(id = R.string.series_lbl))
                SectionContent("Hello Worlds")

                // events
                SectionTitle(stringResource(id = R.string.events_lbl))
                SectionContent("Hello Worlds")

                // related links
                SectionTitle(stringResource(id = R.string.related_links_lbl))

                ExternalLinkText(
                    context,
                    stringResource(id = R.string.detail_lbl),
                    character.detailLink
                )
                ExternalLinkText(
                    context,
                    stringResource(id = R.string.wiki_lbl),
                    character.wikiLink
                )
                ExternalLinkText(
                    context,
                    stringResource(id = R.string.comic_link_lbl),
                    character.comicLink
                )
            }
        }
    }
}

//@Composable
//@Preview(showBackground = true)
//fun CharacterDetailsScreenPreview() {
//    CharacterDetailsScreenContent()
//}