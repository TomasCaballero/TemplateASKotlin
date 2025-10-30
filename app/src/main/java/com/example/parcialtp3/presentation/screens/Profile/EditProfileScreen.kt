package com.example.parcialtp3.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.parcialtp3.R
import com.example.parcialtp3.ui.theme.*

@Composable
fun EditProfileScreen(
    userName: String = "John Smith",
    userId: String = "25030024",
    userPhone: String = "+44 555 5555 55",
    userEmail: String = "example@example.com",
    onBackClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onCameraClick: () -> Unit = {},
    onUpdateProfile: (String, String, String, Boolean, Boolean) -> Unit = { _, _, _, _, _ -> }
) {
    // Estados para los campos
    var username by remember { mutableStateOf(userName) }
    var phone by remember { mutableStateOf(userPhone) }
    var email by remember { mutableStateOf(userEmail) }
    var pushNotifications by remember { mutableStateOf(true) }
    var darkTheme by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FinGreenLight)
    ) {
        // 1. FONDO VERDE (Header) - MÁS BAJO
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp) // ANTES 200dp (MÁS BAJO)
                .background(FinGreen)
                .zIndex(1f)
        ) {
            // Top Navigation Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp) // ANTES 16dp
                    .statusBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back button
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.navbar_home),
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Title
                Text(
                    text = "Edit My Profile",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
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
                        tint = FinGreen,
                        modifier = Modifier.size(20.dp) // ANTES 20dp
                    )
                }
            }
        }

        // 2. CARD VERDE CLARO - MÁS ARRIBA
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 150.dp)
                .zIndex(2f)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                color = FinGreenLight,
                shadowElevation = 0.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(top = 120.dp) // ANTES 120dp (MENOS ESPACIO)
                        .padding(horizontal = 24.dp) // ANTES 24dp
                        .padding(bottom = 24.dp) // ANTES 24dp
                ) {
                    // Account Settings Title - MÁS PEQUEÑO
                    Text(
                        text = "Account Settings",
                        fontSize = 18.sp, // ANTES 18sp
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 10.dp) // ANTES 12dp
                    )

                    // Username Field
                    EditProfileTextField(
                        label = "Username",
                        value = username,
                        onValueChange = { username = it }
                    )

                    Spacer(modifier = Modifier.height(5.dp)) // ANTES 12dp

                    // Phone Field
                    EditProfileTextField(
                        label = "Phone",
                        value = phone,
                        onValueChange = { phone = it },
                        keyboardType = KeyboardType.Phone
                    )

                    Spacer(modifier = Modifier.height(10.dp)) // ANTES 12dp

                    // Email Field
                    EditProfileTextField(
                        label = "Email Address",
                        value = email,
                        onValueChange = { email = it },
                        keyboardType = KeyboardType.Email
                    )

                    Spacer(modifier = Modifier.height(12.dp)) // ANTES 16dp

                    // Push Notifications Toggle
                    EditProfileToggle(
                        label = "Push Notifications",
                        checked = pushNotifications,
                        onCheckedChange = { pushNotifications = it }
                    )

                    Spacer(modifier = Modifier.height(6.dp)) // ANTES 8dp

                    // Dark Theme Toggle
                    EditProfileToggle(
                        label = "Turn Dark Theme",
                        checked = darkTheme,
                        onCheckedChange = { darkTheme = it }
                    )

                    Spacer(modifier = Modifier.height(18.dp)) // ANTES 24dp

                    // UPDATE PROFILE BUTTON
                    Button(
                        onClick = {
                            onUpdateProfile(username, phone, email, pushNotifications, darkTheme)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp), // ANTES 48dp
                        colors = ButtonDefaults.buttonColors(
                            containerColor = FinGreen
                        ),
                        shape = RoundedCornerShape(26.dp)
                    ) {
                        Text(
                            text = "Update Profile",
                            fontSize = 15.sp, // ANTES 16sp
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }

        // 3. FOTO DE PERFIL - MÁS PEQUEÑA Y MÁS ARRIBA
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 90.dp) // ANTES 100dp (MÁS ARRIBA)
                .zIndex(3f),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Foto con botón de cámara - MÁS PEQUEÑA
                Box(
                    modifier = Modifier.size(110.dp) // ANTES 120dp
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_avatar),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    // Botón de cámara - MÁS PEQUEÑO
                    Box(
                        modifier = Modifier
                            .size(30.dp) // ANTES 34dp
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(FinGreen)
                            .clickable(onClick = onCameraClick),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.camara_profile),
                            contentDescription = "Edit Photo",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp) // ANTES 16dp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp)) // ANTES 10dp

                // Nombre - MÁS PEQUEÑO
                Text(
                    text = userName,
                    fontSize = 17.sp, // ANTES 18sp
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(2.dp))

                // ID - MÁS PEQUEÑO
                Text(
                    text = "ID: $userId",
                    fontSize = 11.sp, // ANTES 12sp
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun EditProfileTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column {
        Text(
            text = label,
            fontSize = 13.sp, // ANTES 13sp (MÁS PEQUEÑO)
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 5.dp) // ANTES 6dp
        )

        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(47.dp), // ANTES 50dp (MÁS BAJO)
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE6F0FF),
                unfocusedContainerColor = Color(0xFFE6F0FF),
                disabledContainerColor = Color(0xFFE6F0FF),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedTextColor = Color.DarkGray,
                unfocusedTextColor = Color.DarkGray
            ),
            shape = RoundedCornerShape(5.dp), // ANTES 12dp
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            textStyle = LocalTextStyle.current.copy(fontSize = 10.sp) // ANTES 14sp
        )
    }
}

@Composable
fun EditProfileToggle(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), // ANTES 6dp (MÁS COMPACTO)
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp, // ANTES 15sp (MÁS PEQUEÑO)
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = FinGreen,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.LightGray
            )
        )
    }
}