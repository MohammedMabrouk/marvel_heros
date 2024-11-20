package com.mabrouk.mohamed.marvelheros.presentation.screens.charactersList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mabrouk.mohamed.marvelheros.R
import com.mabrouk.mohamed.marvelheros.domain.data.CharacterItem
import com.mabrouk.mohamed.marvelheros.presentation.component.ProgressIndicator


@Composable
fun CharactersList(
    modifier: Modifier,
    charactersList: List<CharacterItem?>?,
    isLoading: Boolean,
    isLastPageReached: Boolean,
    loadNextPage: () -> Unit,
    onItemClicked: (Int?) -> Unit,
) {
    val listState = rememberLazyListState()

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        if (!charactersList.isNullOrEmpty() || isLoading) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                charactersList?.let {
                    items(it.size) { index ->
                        it[index]?.let { item ->
                            CharacterUiItem(item) {onItemClicked(item.id)}

                            // last element in list
                            if (index == it.size - 1 && !isLoading && !isLastPageReached) {
                                LaunchedEffect(Unit) {
                                    loadNextPage()
                                }
                            }
                        }
                    }
                }

                item {
                    if (isLoading) {
                        ProgressIndicator()
                    }
                }
            }
        } else if (charactersList != null && charactersList.isEmpty()) {
            NoCharactersCard(Modifier.fillMaxSize())
        }
    }
}

@Composable
fun NoCharactersCard(modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(vertical = 12.dp),
            text = stringResource(id = R.string.no_data_msg),
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.white),
        )
    }
}