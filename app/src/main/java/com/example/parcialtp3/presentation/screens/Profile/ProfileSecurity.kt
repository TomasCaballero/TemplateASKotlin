package com.example.parcialtp3.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.parcialtp3.R
import com.example.parcialtp3.ui.theme.*




@Composable
fun SecurityScreen(
    onBackClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onChangePinClick: () -> Unit = {},
    onFingerprintClick: () -> Unit = {},
    onTermsClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGreen)
    ) {
        // 1. HEADER VERDE
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(MainGreen)
                .zIndex(1f)
        ) {
            // Top Navigation Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .statusBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back button
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.bring_back),
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Title
                Text(
                    text = "Security",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = PoppinsFontFamily,
                    color = Color.Black
                )

                // Notification button
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable(onClick = onNotificationClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.home_campana),
                        contentDescription = "Notifications",
                        tint = MainGreen,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // 2. CONTENIDO CON CARD BLANCO
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 160.dp)
                .zIndex(2f)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                color = LightGreen,
                shadowElevation = 0.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                        .padding(top = 32.dp)
                ) {
                    // Título "Security"
                    Text(
                        text = "Security",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Card blanca con las opciones
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Change Pin
                            SecurityMenuItem(
                                text = "Change Pin",
                                onClick = onChangePinClick,
                                showDivider = true
                            )

                            // Fingerprint
                            SecurityMenuItem(
                                text = "Fingerprint",
                                onClick = onFingerprintClick,
                                showDivider = true
                            )

                            // Terms And Conditions
                            SecurityMenuItem(
                                text = "Terms And Conditions",
                                onClick = onTermsClick,
                                showDivider = false
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SecurityMenuItem(
    text: String,
    onClick: () -> Unit,
    showDivider: Boolean = true
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Texto
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = PoppinsFontFamily,
                color = Color.DarkGray
            )

            // Flecha derecha
            Icon(
                painter = painterResource(id = R.drawable.vector),
                contentDescription = "Go",
                tint = Color.LightGray,
                modifier = Modifier.size(20.dp)
            )
        }

        // Divider
        if (showDivider) {
            Divider(
                modifier = Modifier.padding(horizontal = 24.dp),
                color = Color.LightGray.copy(alpha = 0.2f),
                thickness = 1.dp
            )
        }
    }
}