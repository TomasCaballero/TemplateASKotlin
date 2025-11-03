package com.example.parcialtp3.presentation.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.parcialtp3.R
import com.example.parcialtp3.domain.model.NavigationItem
import com.example.parcialtp3.presentation.components.BottomNavBar
import com.example.parcialtp3.ui.theme.*

@Composable
fun ProfileScreen(
    userName: String = "John Smith",
    userId: String = "25030024",
    onBackClick: () -> Unit = {},
    onNavigationItemSelected: (NavigationItem) -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onEditProfileClick: () -> Unit = {},
    onSecurityClick: () -> Unit = {},
    onSettingClick: () -> Unit = {},
    onHelpClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
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
                            painter = painterResource(id = R.drawable.navbar_home),
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

                    // Notification button con círculo blanco
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
                            onLogoutClick = onLogoutClick
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