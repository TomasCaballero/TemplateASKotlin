package com.example.parcialtp3.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcialtp3.R
import com.example.parcialtp3.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SuccessfullyScreen(
    message: String = "Pin Has Been\nChanged Successfully",
    onNavigateBack: () -> Unit = {}
) {
    // Auto-navegar después de 2 segundos
    LaunchedEffect(Unit) {
        delay(2000)
        onNavigateBack()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainGreen),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.check_progress),
                contentDescription = "Success",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Mensaje
            Text(
                text = message,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsFontFamily,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 36.sp
            )
        }
    }
}