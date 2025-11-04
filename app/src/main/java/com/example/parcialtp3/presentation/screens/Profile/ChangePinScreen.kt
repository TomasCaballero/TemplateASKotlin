package com.example.parcialtp3.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.parcialtp3.R
import com.example.parcialtp3.presentation.components.NotificationButton
import com.example.parcialtp3.ui.theme.*

@Composable
fun ChangePinScreen(
    onBackClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onSuccess: (String) -> Unit  // ← QUITAR el = {}
 )
 {
    var currentPin by remember { mutableStateOf("") }
    var newPin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }
    var currentPinVisible by remember { mutableStateOf(false) }
    var newPinVisible by remember { mutableStateOf(false) }
    var confirmPinVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGreen)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(MainGreen)
                .zIndex(1f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .statusBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.bring_back),
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "Change Pin",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = PoppinsFontFamily,
                    color = Color.Black
                )

                NotificationButton(onClick = onNotificationClick)
            }
        }

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
                    PinTextField(
                        label = "Current Pin",
                        value = currentPin,
                        onValueChange = { currentPin = it },
                        isVisible = currentPinVisible,
                        onVisibilityToggle = { currentPinVisible = !currentPinVisible }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    PinTextField(
                        label = "New Pin",
                        value = newPin,
                        onValueChange = { newPin = it },
                        isVisible = newPinVisible,
                        onVisibilityToggle = { newPinVisible = !newPinVisible }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    PinTextField(
                        label = "Confirm Pin",
                        value = confirmPin,
                        onValueChange = { confirmPin = it },
                        isVisible = confirmPinVisible,
                        onVisibilityToggle = { confirmPinVisible = !confirmPinVisible }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            println("🔴 BOTÓN PRESIONADO")
                            println("🔴 Llamando onSuccess con mensaje...")
                            val message = "Pin Has Been\nChanged Successfully"
                            println("🔴 Mensaje: $message")
                            onSuccess(message)
                            println("🔴 DESPUÉS DE onSuccess")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MainGreen),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = "Change Pin",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = PoppinsFontFamily,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PinTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isVisible: Boolean,
    onVisibilityToggle: () -> Unit
) {
    Column {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = PoppinsFontFamily,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    "●●●●●●",
                    color = Color.Gray,
                    fontFamily = PoppinsFontFamily
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            singleLine = true,
            visualTransformation = if (isVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            trailingIcon = {
                IconButton(onClick = onVisibilityToggle) {
                    Icon(
                        painter = painterResource(id = R.drawable.eye_pass),
                        contentDescription = if (isVisible) "Hide" else "Show",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(24.dp)
                            .rotate(if (isVisible) 180f else 0f)
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFDFF7E2),
                unfocusedContainerColor = Color(0xFFDFF7E2),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.DarkGray,
                unfocusedTextColor = Color.DarkGray
            ),
            shape = RoundedCornerShape(8.dp),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = PoppinsFontFamily
            )
        )
    }
}
