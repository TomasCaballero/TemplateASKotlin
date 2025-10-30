package com.example.parcialtp3.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcialtp3.R
import com.example.parcialtp3.ui.theme.FinGreen
import com.example.parcialtp3.ui.theme.ParcialTP3Theme
import kotlinx.coroutines.delay

/**
 * Splash Screen inicial de FinWise
 * Se muestra por 2 segundos antes de navegar a LaunchScreen
 */
@Composable
fun SplashScreen(
    onNavigateToLaunch: () -> Unit = {}
) {
    // Navegación automática después de 2 segundos
    LaunchedEffect(Unit) {
        delay(2000)
        onNavigateToLaunch()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = FinGreen
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo (drawable con color oscuro)
            Image(
                painter = painterResource(id = R.drawable.launch_logo),
                contentDescription = "FinWise Logo",
                modifier = Modifier.size(96.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Nombre de la app en blanco
            Text(
                text = "FinWise",
                fontSize = 60.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                letterSpacing = (-0.5).sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    ParcialTP3Theme {
        SplashScreen()
    }
}
