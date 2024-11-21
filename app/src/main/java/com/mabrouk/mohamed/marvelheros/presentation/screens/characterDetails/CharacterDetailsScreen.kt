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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.graphicsLayer
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
import com.mabrouk.mohamed.marvelheros.domain.data.CharacterDataType
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

    CharacterDetailsScreenContent(
        onBackClicked = { navController.popBackStack() },
        viewModel = viewModel
    )
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterDetailsScreenContent(
    onBackClicked: () -> Unit,
    viewModel: CharacterDetailsViewModel,
) {

    // Error State
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val character by viewModel.character.collectAsState()

    val comicsList by viewModel.comicsList.collectAsState()
    val isLastPageComicsReached by viewModel.isLastPageComicsReached.collectAsState()

    val seriesList by viewModel.seriesList.collectAsState()
    val isLastPageSeriesReached by viewModel.isLastPageSeriesReached.collectAsState()

    val storiesList by viewModel.storiesList.collectAsState()
    val isLastPageStoriesReached by viewModel.isLastPageStoriesReached.collectAsState()

    val eventsList by viewModel.eventsList.collectAsState()
    val isLastPageEventsReached by viewModel.isLastPageEventsReached.collectAsState()

    val showImagesDialog by viewModel.showImagesDialog.collectAsState()
    val dialogStartIndex by viewModel.dialogStartIndex.collectAsState()
    val dialogType by viewModel.dialogType.collectAsState()

    if (showImagesDialog) {
        when (dialogType) {
            CharacterDataType.COMIC -> {
                ExpandedImagesDialog(
                    onDismiss = {
                        viewModel.setShowImagesDialog(false)
                        viewModel.setDialogStartIndex(0)
                    },
                    characterDataItemList = comicsList,
                    isLastPageReached = isLastPageComicsReached,
                    loadNextPage = { viewModel.getComics(false) },
                    pageSize = viewModel.pageSize,
                    initialPosition = dialogStartIndex
                )
            }

            CharacterDataType.SERIES -> {
                ExpandedImagesDialog(
                    onDismiss = {
                        viewModel.setShowImagesDialog(false)
                        viewModel.setDialogStartIndex(0)
                    },
                    characterDataItemList = seriesList,
                    isLastPageReached = isLastPageSeriesReached,
                    loadNextPage = { viewModel.getSeries(false) },
                    pageSize = viewModel.pageSize,
                    initialPosition = dialogStartIndex
                )
            }

            CharacterDataType.STORY -> {
                ExpandedImagesDialog(
                    onDismiss = {
                        viewModel.setShowImagesDialog(false)
                        viewModel.setDialogStartIndex(0)
                    },
                    characterDataItemList = storiesList,
                    isLastPageReached = isLastPageStoriesReached,
                    loadNextPage = { viewModel.getStories(false) },
                    pageSize = viewModel.pageSize,
                    initialPosition = dialogStartIndex
                )
            }

            CharacterDataType.EVENT -> {
                ExpandedImagesDialog(
                    onDismiss = {
                        viewModel.setShowImagesDialog(false)
                        viewModel.setDialogStartIndex(0)
                    },
                    characterDataItemList = eventsList,
                    isLastPageReached = isLastPageEventsReached,
                    loadNextPage = { viewModel.getEvents(false) },
                    pageSize = viewModel.pageSize,
                    initialPosition = dialogStartIndex
                )
            }
        }

    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        character?.let { currentCharacter ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                if (currentCharacter.thumbnailUrl.isNotEmpty()) {
                    GlideImage(
                        model = Uri.parse(currentCharacter.thumbnailUrl),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { alpha = 1f }
                        .background(Color.Black.copy(alpha = 0.95f))
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
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
                            if (currentCharacter.thumbnailUrl.isNotEmpty()) {
                                GlideImage(
                                    model = Uri.parse(currentCharacter.thumbnailUrl),
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

                    // name
                    if (currentCharacter.name.isNotEmpty()) {
                        SectionTitle(stringResource(id = R.string.name_lbl))
                        SectionContent(currentCharacter.name)
                    }

                    // description
                    if (!currentCharacter.description.isNullOrEmpty()) {
                        SectionTitle(stringResource(id = R.string.description_lbl))
                        SectionContent(currentCharacter.description)
                    }

                    // comics
                    if (comicsList.isNotEmpty()) {
                        SectionTitle(stringResource(id = R.string.comics_lbl))
                        CharacterInfoList(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .padding(bottom = 12.dp),
                            characterDataItemList = comicsList,
                            isLoading = false,
                            isLastPageReached = isLastPageComicsReached,
                            loadNextPage = { viewModel.getComics(false) },
                            onItemClicked = { type, index ->
                                viewModel.setDialogStartIndex(index)
                                viewModel.setDialogType(type)
                                viewModel.setShowImagesDialog(true)
                            },
                            pageSize = viewModel.pageSize,
                            type = CharacterDataType.COMIC
                        )
                    }

                    // series
                    if (seriesList.isNotEmpty()) {
                        SectionTitle(stringResource(id = R.string.series_lbl))
                        CharacterInfoList(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .padding(bottom = 12.dp),
                            characterDataItemList = seriesList,
                            isLoading = false,
                            isLastPageReached = isLastPageSeriesReached,
                            loadNextPage = { viewModel.getSeries(false) },
                            onItemClicked = { type, index ->
                                viewModel.setDialogStartIndex(index)
                                viewModel.setDialogType(type)
                                viewModel.setShowImagesDialog(true)
                            },
                            pageSize = viewModel.pageSize,
                            type = CharacterDataType.SERIES
                        )
                    }

                    // stories
                    if (storiesList.isNotEmpty()) {
                        SectionTitle(stringResource(id = R.string.stories_lbl))
                        CharacterInfoList(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .padding(bottom = 12.dp),
                            characterDataItemList = storiesList,
                            isLoading = false,
                            isLastPageReached = isLastPageStoriesReached,
                            loadNextPage = { viewModel.getStories(false) },
                            onItemClicked = { type, index ->
                                viewModel.setDialogStartIndex(index)
                                viewModel.setDialogType(type)
                                viewModel.setShowImagesDialog(true)
                            },
                            pageSize = viewModel.pageSize,
                            type = CharacterDataType.STORY
                        )
                    }

                    // events
                    if (eventsList.isNotEmpty()) {
                        SectionTitle(stringResource(id = R.string.events_lbl))
                        CharacterInfoList(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .padding(bottom = 12.dp),
                            characterDataItemList = eventsList,
                            isLoading = false,
                            isLastPageReached = isLastPageEventsReached,
                            loadNextPage = { viewModel.getEvents(false) },
                            onItemClicked = { type, index ->
                                viewModel.setDialogStartIndex(index)
                                viewModel.setDialogType(type)
                                viewModel.setShowImagesDialog(true)
                            },
                            pageSize = viewModel.pageSize,
                            type = CharacterDataType.EVENT
                        )
                    }

                    // related links
                    SectionTitle(stringResource(id = R.string.related_links_lbl))

                    ExternalLinkText(
                        context,
                        stringResource(id = R.string.detail_lbl),
                        currentCharacter.detailLink
                    )
                    ExternalLinkText(
                        context,
                        stringResource(id = R.string.wiki_lbl),
                        currentCharacter.wikiLink
                    )
                    ExternalLinkText(
                        context,
                        stringResource(id = R.string.comic_link_lbl),
                        currentCharacter.comicLink
                    )
                }
            }
        }
    }
}
//
//@Composable
//@Preview(showBackground = true)
//fun CharacterDetailsScreenPreview() {
//    CharacterDetailsScreenContent(
//        {},
//        character = CharacterItem(
//            id = 0,
//            name = "name",
//            thumbnailUrl = "",
//            description = "description",
//            comicsList = null,
//            seriesList = null,
//            storiesList = null,
//            eventsList = null,
//            detailLink = null,
//            wikiLink = null,
//            comicLink = null,
//        )
//    )
//}