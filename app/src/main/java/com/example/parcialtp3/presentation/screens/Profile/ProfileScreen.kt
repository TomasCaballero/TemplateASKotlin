package com.example.parcialtp3.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.parcialtp3.R
import com.example.parcialtp3.domain.model.NavigationItem
import com.example.parcialtp3.presentation.components.BottomNavBar
import com.example.parcialtp3.presentation.components.NotificationButton
import com.example.parcialtp3.presentation.components.LoadingIndicator
import com.example.parcialtp3.presentation.components.ErrorMessage
import com.example.parcialtp3.presentation.viewmodels.ProfileViewModel
import com.example.parcialtp3.presentation.viewmodels.ProfileUiState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import com.example.parcialtp3.ui.theme.*

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onNavigationItemSelected: (NavigationItem) -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onEditProfileClick: () -> Unit = {},
    onSecurityClick: () -> Unit = {},
    onSettingClick: () -> Unit = {},
    onHelpClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    // Obtener datos del usuario de la API o usar mock
    val userName = when (uiState) {
        is ProfileUiState.Success -> (uiState as ProfileUiState.Success).user.username
        else -> "Loading..."
    }

    val userId = when (uiState) {
        is ProfileUiState.Success -> (uiState as ProfileUiState.Success).user.id.toString()
        else -> "..."
    }

    Scaffold(
        containerColor = LightGreen,
        bottomBar = {
            BottomNavBar(
                selectedItem = NavigationItem.PROFILE,
                onItemSelected = onNavigationItemSelected
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(LightGreen)
        ) {
            // 1. FONDO VERDE (Header)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
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
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    // Title
                    Text(
                        text = "Profile",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = PoppinsFontFamily,
                        color = LettersAndIcons
                    )

                    // Notification button
                    NotificationButton(onNotificationClick = onNotificationClick)
                }
            }

            // 2. CARD VERDE CLARO SUPERPUESTO
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 180.dp)
                    .zIndex(2f)
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                    color = BackgroundGreenWhiteAndLetters,
                    shadowElevation = 0.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 110.dp)
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 24.dp)
                    ) {
                        // Nombre
                        Text(
                            text = userName,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = PoppinsFontFamily,
                            color = DarkModeGreenBar,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // ID
                        Text(
                            text = "ID: $userId",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = PoppinsFontFamily,
                            color = LettersAndIcons,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Lista de opciones del menú
                        ProfileMenuList(
                            onEditProfileClick = onEditProfileClick,
                            onSecurityClick = onSecurityClick,
                            onSettingClick = onSettingClick,
                            onHelpClick = onHelpClick,
                            onLogoutClick = { showLogoutDialog = true }
                        )
                    }
                }
            }

            // 3. FOTO DE PERFIL
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 110.dp)
                    .zIndex(3f),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_avatar),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(130.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Diálogo de confirmación de logout
        if (showLogoutDialog) {
            LogoutConfirmationDialog(
                onDismiss = { showLogoutDialog = false },
                onConfirm = {
                    showLogoutDialog = false
                    viewModel.logout()
                    onLogoutClick()
                }
            )
        }
    }
}

@Composable
fun ProfileMenuList(
    onEditProfileClick: () -> Unit,
    onSecurityClick: () -> Unit,
    onSettingClick: () -> Unit,
    onHelpClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // EDIT PROFILE
        ProfileMenuItem(
            iconRes = R.drawable.icon_profile,
            title = "Edit Profile",
            onClick = onEditProfileClick
        )

        // SECURITY
        ProfileMenuItem(
            iconRes = R.drawable.icon_security,
            title = "Security",
            onClick = onSecurityClick
        )

        // SETTING
        ProfileMenuItem(
            iconRes = R.drawable.icon_setting,
            title = "Setting",
            onClick = onSettingClick
        )

        // HELP
        ProfileMenuItem(
            iconRes = R.drawable.icon_help,
            title = "Help",
            onClick = onHelpClick
        )

        // LOGOUT
        ProfileMenuItem(
            iconRes = R.drawable.icon_logout,
            title = "Logout",
            onClick = onLogoutClick
        )
    }
}

@Composable
fun ProfileMenuItem(
    iconRes: Int,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Sin círculo - solo el icono
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = title,
            modifier = Modifier.size(56.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Title
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = PoppinsFontFamily,
            color = Color.Black
        )
    }
}

@Composable
fun LogoutConfirmationDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    // Colores personalizados del diseño HTML
    val emeraldColor = Color(0xFF0D9488)
    val mintLightColor = Color(0xFFE6FFE6)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f))
            .clickable(
                onClick = onDismiss,
                indication = null,
                interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
            ),
        contentAlignment = Alignment.Center
    ) {
        // Tarjeta blanca del diálogo
        Surface(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
                .clickable(
                    onClick = {},
                    indication = null,
                    interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                ),
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            shadowElevation = 24.dp
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título "End Session"
                Text(
                    text = "End Session",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFontFamily,
                    color = emeraldColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Pregunta
                Text(
                    text = "Are you sure you want to log out?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = PoppinsFontFamily,
                    color = Color(0xFF374151),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Botón "Yes, End Session"
                Button(
                    onClick = onConfirm,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = emeraldColor
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Text(
                        text = "Yes, End Session",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón "Cancel"
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = mintLightColor
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = PoppinsFontFamily,
                        color = emeraldColor
                    )
                }
            }
        }
    }
}