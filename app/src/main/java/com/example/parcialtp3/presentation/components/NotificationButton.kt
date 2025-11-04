package com.example.parcialtp3.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.parcialtp3.R
import com.example.parcialtp3.ui.theme.LettersAndIcons
import com.example.parcialtp3.ui.theme.LightGreen

@Composable
fun NotificationButton(
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        IconButton(
            onClick = onNotificationClick,
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = LightGreen,
                    shape = RoundedCornerShape(50)
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.home_campana),
                contentDescription = "Notifications",
                colorFilter = ColorFilter.tint(LettersAndIcons),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
