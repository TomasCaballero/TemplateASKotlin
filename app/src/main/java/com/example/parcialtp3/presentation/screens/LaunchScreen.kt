package com.example.parcialtp3.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcialtp3.R
import com.example.parcialtp3.presentation.viewmodels.LoginUiState
import com.example.parcialtp3.ui.theme.*

/**
 * Pantalla de bienvenida/lanzamiento de FinWise
 * Esta es una pantalla estática que no requiere ViewModel
 */
@Composable
fun LaunchScreen(
    onLoginClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundGreenWhiteAndLetters
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo y título
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Logo
                Image(
                    painter = painterResource(id = R.drawable.launch_logoclaro),
                    contentDescription = "FinWise Logo",
                    modifier = Modifier.size(80.dp),
                    colorFilter = ColorFilter.tint(MainGreen)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Nombre de la app
                Text(
                    text = "FinWise",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MainGreen,
                    letterSpacing = (-0.5).sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Eslogan
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.",
                    fontSize = 12.sp,
                    color = BackgroundDarkModeAndLetters,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.widthIn(max = 280.dp),
                    lineHeight = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            // Botones
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 320.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onLoginClick,
                    modifier = Modifier
                        .width(220.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainGreen,
                        contentColor = Color.White,
                        disabledContainerColor = MainGreen.copy(alpha = 0.6f),
                        disabledContentColor = Color.White.copy(alpha = 0.6f)
                    ),
                    shape = RoundedCornerShape(24.dp),
                ) {

                   Text(
                       text = "Log In",
                       fontSize = 20.sp,
                       color = LettersAndIcons,
                       fontWeight = FontWeight.Bold,
                       letterSpacing = 0.5.sp
                   )
                }

                // Botón Sign Up
                Button(
                    onClick = onSignUpClick,
                    modifier = Modifier
                        .width(220.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightGreen,
                        contentColor = Color.White,
                        disabledContainerColor = MainGreen.copy(alpha = 1.0f),
                        disabledContentColor = Color.White.copy(alpha = 1.0f)
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Sign Up",
                        fontSize = 20.sp,
                        color = LettersAndIcons,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            // Enlace Forgot Password
            TextButton(onClick = onForgotPasswordClick) {
                Text(
                    text = "Forgot Password?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LaunchScreenPreview() {
    ParcialTP3Theme {
        LaunchScreen()
    }
}
