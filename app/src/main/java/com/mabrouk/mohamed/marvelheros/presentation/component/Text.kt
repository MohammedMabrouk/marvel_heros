package com.mabrouk.mohamed.marvelheros.presentation.component

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mabrouk.mohamed.marvelheros.R
import com.mabrouk.mohamed.marvelheros.presentation.utils.openWebPage

@Composable
fun SectionTitle(
    text: String,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(top = 12.dp)
            .padding(bottom = 8.dp),
        text = text.toUpperCase(),
        fontSize = 12.sp,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(id = R.color.red),
    )
}

@Composable
fun SectionContent(
    text: String,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        text = text,
        fontSize = 18.sp,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.white),
    )
}

@Composable
fun ExternalLinkText(
    context: Context,
    text: String,
    link: String?,
) {
    if (!link.isNullOrEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .clickable { openWebPage(context, link) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = text,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = R.color.white),
            )
            Icon(
                painter = painterResource(R.drawable.ic_open),
                contentDescription = "search",
                tint = colorResource(id = R.color.white),
                modifier = Modifier
                    .padding(start = 12.dp)
                    .size(18.dp)
            )
        }
    }
}