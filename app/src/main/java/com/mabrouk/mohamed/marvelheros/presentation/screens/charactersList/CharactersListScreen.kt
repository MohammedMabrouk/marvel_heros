package com.mabrouk.mohamed.marvelheros.presentation.screens.charactersList

import android.net.Uri
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
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mabrouk.mohamed.marvelheros.R
import com.mabrouk.mohamed.marvelheros.domain.data.CharacterItem
import com.mabrouk.mohamed.marvelheros.presentation.component.LottieAnimation
import com.mabrouk.mohamed.marvelheros.presentation.navigation.Screen

@Composable
fun CharactersListScreen(
    navController: NavHostController,
) {
    CharactersListScreenContent(
        onSearchClicked = { navController.navigate(Screen.Search.route) }
    )
}


@Composable
fun CharactersListScreenContent(
    onSearchClicked: () -> Unit,
) {
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
        CharacterUiItem(
            CharacterItem(
                1,
                "3-D Man",
                "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg"
            )
        )
        Box(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            LottieAnimation(
                modifier = Modifier.size(35.dp),
                R.raw.loading
            )
        }
    }
}

//@Composable
//@Preview(showBackground = true, apiLevel = 28)
//fun CharactersListScreenPreview() {
//    CharactersListScreenContent({})
//}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterUiItem(
    characterItem: CharacterItem,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        if (characterItem.thumbnailUrl.isNotEmpty()) {
            GlideImage(
                model = Uri.parse(characterItem.thumbnailUrl),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        } else {
            Image(
                painter = painterResource(R.drawable.ic_no_image),
                contentDescription = "",
                colorFilter = ColorFilter.tint(colorResource(id = R.color.white)),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
            )
        }
        // Transparent color overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.2f }
                .background(colorResource(id = R.color.black))
        )

        // character name
        Box(
            modifier = Modifier
                .padding(start = 18.dp)
                .padding(bottom = 24.dp)
                .background(
                    color = colorResource(id = R.color.white),
                    shape = CutCornerShape(
                        topStartPercent = 100,
                        topEndPercent = 0,
                        bottomEndPercent = 100,
                        bottomStartPercent = 0
                    )
                )
                .align(Alignment.BottomStart)

        ) {
            Text(
                modifier = Modifier.padding(vertical = 2.dp, horizontal = 34.dp),
                text = characterItem.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.black),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview
fun CharacterUiItemPreview() {
    CharacterUiItem(
        CharacterItem(
            1,
            "3-D Man",
            "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg"
        )
    )
}