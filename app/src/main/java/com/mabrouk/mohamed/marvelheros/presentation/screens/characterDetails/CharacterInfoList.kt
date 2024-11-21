package com.mabrouk.mohamed.marvelheros.presentation.screens.characterDetails

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mabrouk.mohamed.marvelheros.R
import com.mabrouk.mohamed.marvelheros.domain.data.CharacterDataItem
import com.mabrouk.mohamed.marvelheros.domain.data.CharacterDataType
import com.mabrouk.mohamed.marvelheros.presentation.component.ProgressIndicator

@Composable
fun CharacterInfoList(
    modifier: Modifier,
    characterDataItemList: List<CharacterDataItem?>?,
    isLoading: Boolean,
    isLastPageReached: Boolean,
    loadNextPage: () -> Unit,
    onItemClicked: (CharacterDataType, Int) -> Unit,
    pageSize: Int,
    type: CharacterDataType,
    isExpandedView: Boolean = false,
    initialPosition: Int = 0,
) {
    val listState = rememberLazyListState()
    LaunchedEffect(Unit) {
        if (
            characterDataItemList != null &&
            initialPosition > 0 &&
            initialPosition < characterDataItemList.size
        ) {
            listState.scrollToItem(initialPosition)
        }
    }
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        if (!characterDataItemList.isNullOrEmpty() || isLoading) {
            LazyRow(
                state = listState,
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                characterDataItemList?.let {
                    items(it.size) { index ->
                        it[index]?.let { item ->
                            CharacterInfoItem(
                                index,
                                item,
                                { onItemClicked(type, index) },
                                isExpandedView
                            )

                            // last element in list
                            if (index == it.size - 1 && it.size >= pageSize && !isLoading && !isLastPageReached) {
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
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterInfoItem(
    index: Int,
    characterDataItem: CharacterDataItem,
    onItemClicked: (Int) -> Unit,
    isExpandedView: Boolean,
) {
    val totalHeight = if (isExpandedView) 500 else 160
    val totalWidth = if (isExpandedView) 300 else 110
    val imageHeight = if (isExpandedView) 400 else 120
    val errorImageHeight = if (isExpandedView) 380 else 110
    val textSize = if (isExpandedView) 18 else 10
    val lineHeight = if (isExpandedView) 18 else 14
    val scaleType = if (isExpandedView) ContentScale.Fit else ContentScale.Crop

    Column(
        modifier = Modifier
            .height(totalHeight.dp)
            .width(totalWidth.dp)
            .padding(horizontal = 4.dp)
            .clickable { onItemClicked(index) }
    ) {

        if (characterDataItem.thumbnailUrl.isNotEmpty()) {
            GlideImage(
                model = Uri.parse(characterDataItem.thumbnailUrl),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight.dp),
                contentScale = scaleType,
                alignment = Alignment.Center
            )
        } else {
            Image(
                painter = painterResource(R.drawable.ic_no_image),
                contentDescription = "",
                colorFilter = ColorFilter.tint(colorResource(id = R.color.white)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(errorImageHeight.dp)
                    .padding(18.dp),
                alignment = Alignment.Center
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            text = characterDataItem.name,
            style = TextStyle(
                fontSize = textSize.sp,
                lineHeight = lineHeight.sp
            ),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.white),
        )
    }
}