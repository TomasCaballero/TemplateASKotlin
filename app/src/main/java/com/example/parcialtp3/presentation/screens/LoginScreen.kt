package com.example.parcialtp3.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.parcialtp3.R
import com.example.parcialtp3.presentation.viewmodels.LoginViewModel
import com.example.parcialtp3.presentation.viewmodels.LoginUiState
import com.example.parcialtp3.ui.theme.*

/**
 * Pantalla de inicio de sesión de FinWise
 * Convertida desde login.html con diseño de card flotante
 */
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onGoogleLoginClick: () -> Unit = {},
    onFacebookLoginClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    // Observar el estado del ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Manejar los diferentes estados
    LaunchedEffect(uiState) {
        when (uiState) {
            is LoginUiState.Success -> {
                onLoginSuccess()
                viewModel.resetState()
            }
            else -> { /* Otros estados se manejan en el UI */ }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FinGreenLight)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Section (Curved mint green banner)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(bottomStart = 64.dp, bottomEnd = 64.dp))
                    .background(FinGreenCard),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = "Welcome",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = LettersAndIcons,
                    modifier = Modifier.padding(top = 64.dp)
                )
            }

            // Main Form Card (White rounded container overlapping header)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .offset(y = (-80).dp),
                shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
                colors = CardDefaults.cardColors(containerColor = BackgroundGreenWhiteAndLetters),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Username/Email Field
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Username Or Email",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LettersAndIcons,
                            modifier = Modifier.padding(bottom = 8.dp, start = 20.dp)
                        )
                        TextField(

                            value = email,
                            onValueChange = { email = it },
                            placeholder = { Text("example@example.com", color = Color.Gray)},
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = LightGreen,
                                unfocusedContainerColor = LightGreen,
                                focusedIndicatorColor = FinGreen,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = FinGreen
                            ),
                            shape = RoundedCornerShape(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password Field
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Password",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LettersAndIcons,
                            modifier = Modifier.padding(bottom = 8.dp, start = 20.dp)
                        )
                        TextField(
                            value = password,
                            onValueChange = { password = it },
                            placeholder = { Text("●●●●●●●●", color = Color.Gray) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            visualTransformation = if (passwordVisible) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                    if (email.isNotBlank() && password.isNotBlank()) {
                                        viewModel.login(email, password)
                                    }
                                }
                            ),
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Text(
                                        text = "👁",
                                        fontSize = 20.sp,
                                        color = Color.Gray
                                    )
                                }
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = LightGreen,
                                unfocusedContainerColor = LightGreen,
                                focusedIndicatorColor = MainGreen,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = MainGreen,
                                focusedTextColor = LettersAndIcons,
                                unfocusedTextColor = LettersAndIcons
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Mostrar mensaje de error si existe
                    if (uiState is LoginUiState.Error) {
                        Text(
                            text = (uiState as LoginUiState.Error).message,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    // Log In Button (Primary Action)
                    Button(
                        onClick = {
                            if (email.isNotBlank() && password.isNotBlank()) {
                                viewModel.login(email, password)
                            }
                        },
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
                        enabled = email.isNotBlank() && password.isNotBlank() && uiState !is LoginUiState.Loading
                    ) {
                        if (uiState is LoginUiState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "Log In",
                                fontSize = 20.sp,
                                color = LettersAndIcons,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Forgot Password Link
                    Text(
                        text = "Forgot Password?",
                        fontSize = 13.sp,
                        color = LettersAndIcons,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable { onForgotPasswordClick() }
                            .padding(6.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Sign Up Button (Secondary Action)
                    Button(
                        onClick = onSignUpClick,
                        modifier = Modifier
                            .width(220.dp)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LightGreen,
                            contentColor = Color.White,
                            disabledContainerColor = MainGreen.copy(alpha = 1.0f),  // ← Agregá esto
                            disabledContentColor = Color.White.copy(alpha = 1.0f)   // ← Y esto
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

                    Spacer(modifier = Modifier.height(12.dp))

                    // Fingerprint Access Section
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_huella),
                            contentDescription = "Fingerprint",
                            modifier = Modifier
                                .size(20.dp)
                                .padding(end = 8.dp)
                        )
                        Text(
                            text = "Use ",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LettersAndIcons
                        )
                        Text(
                            text = "Fingerprint",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BlueButton
                        )
                        Text(
                            text = " To Access",
                            fontSize = 14.sp,
                            color = LettersAndIcons,
                            fontWeight = FontWeight.SemiBold,
                            )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Divider/Social Login Prompt
                    Text(
                        text = "or sign up with",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Social Icons (Facebook and Google)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Facebook Icon
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color.LightGray, CircleShape)
                                .clickable { onFacebookLoginClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.login_facebook),
                                contentDescription = "Facebook",
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(32.dp))

                        // Google Icon
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color.LightGray, CircleShape)
                                .clickable { onGoogleLoginClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.login_google),
                                contentDescription = "Google",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Footer Signup Link
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Don't have an account? ",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "Sign Up",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = FinGreen,
                            modifier = Modifier.clickable { onSignUpClick() }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    ParcialTP3Theme {
        LoginScreen()
    }
}
