package com.mabrouk.mohamed.marvelheros.presentation.screens.characterDetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mabrouk.mohamed.marvelheros.R
import com.mabrouk.mohamed.marvelheros.domain.data.CharacterDataItem
import com.mabrouk.mohamed.marvelheros.domain.data.CharacterDataType

@Composable
fun ExpandedImagesDialog(
    onDismiss: () -> Unit,
    characterDataItemList: List<CharacterDataItem?>?,
    isLastPageReached: Boolean,
    loadNextPage: () -> Unit,
    pageSize: Int,
    initialPosition: Int = 0,
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp),
            content = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = "search",
                            tint = colorResource(id = R.color.white),
                            modifier = Modifier
                                .size(30.dp)
                                .clickable { onDismiss() },
                        )
                    }
                    Spacer(modifier = Modifier.height(100.dp))
                    CharacterInfoList(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .padding(bottom = 12.dp),
                        characterDataItemList = characterDataItemList,
                        isLoading = false,
                        isLastPageReached = isLastPageReached,
                        loadNextPage = { loadNextPage() },
                        onItemClicked = { type, index -> },
                        pageSize = pageSize,
                        type = CharacterDataType.COMIC,
                        isExpandedView = true,
                        initialPosition = initialPosition
                    )
                }
            }
        )
    }
}