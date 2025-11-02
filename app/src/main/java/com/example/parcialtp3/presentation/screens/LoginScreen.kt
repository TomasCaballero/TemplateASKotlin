package com.example.parcialtp3.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText

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

    val uiState by viewModel.uiState.collectAsState()

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
            .background(MainGreen)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header Section (on green background)
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 80.dp, bottom = 60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Welcome",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = LettersAndIcons
                    )
                }
            }

            // White section with rounded top corners
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillParentMaxHeight()
                        .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                        .background(BackgroundGreenWhiteAndLetters)
                        .padding(start = 24.dp, top = 100.dp, bottom = 16.dp, end = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    // Username/Email Field
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Username Or Email",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LettersAndIcons,
                            modifier = Modifier.padding(bottom = 2.dp, start = 16.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(42.dp)
                                .background(LightGreen, RoundedCornerShape(24.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            BasicTextField(
                                value = email,
                                onValueChange = { email = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                singleLine = true,
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontSize = 16.sp,
                                    color = DarkModeGreenBar
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Email,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                cursorBrush = SolidColor(FinGreen),
                                decorationBox = { innerTextField ->
                                    if (email.isEmpty()) {
                                        Text(
                                            "example@example.com",
                                            color = DarkModeGreenBar.copy(alpha = 0.6f),
                                            fontSize = 16.sp
                                        )
                                    }
                                    innerTextField()
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Password Field
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Password",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LettersAndIcons,
                            modifier = Modifier.padding(bottom = 2.dp, start = 16.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(42.dp)
                                .background(LightGreen, RoundedCornerShape(24.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            BasicTextField(
                                value = password,
                                onValueChange = { password = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .padding(end = 32.dp),
                                singleLine = true,
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontSize = 20.sp,
                                    color = DarkModeGreenBar
                                ),
                                visualTransformation = if (passwordVisible) {
                                    VisualTransformation.None
                                } else {
                                    SpacedPasswordTransformation()
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
                                cursorBrush = SolidColor(FinGreen),
                                decorationBox = { innerTextField ->
                                    if (password.isEmpty()) {
                                        Text(
                                            "● ● ● ● ● ● ● ●",
                                            color = DarkModeGreenBar.copy(alpha = 0.6f),
                                            fontSize = 20.sp
                                        )
                                    }
                                    innerTextField()
                                }
                            )
                            IconButton(
                                onClick = { passwordVisible = !passwordVisible },
                                modifier = Modifier.align(Alignment.CenterEnd)
                            ) {
                                Image(
                                    painter = painterResource(
                                        id = if (passwordVisible) R.drawable.eye_pass_open else R.drawable.eye_pass
                                    ),
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                    modifier = Modifier.size(20.dp),
                                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(DarkModeGreenBar)
                                )
                            }
                        }
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

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.login_facebook),
                            contentDescription = "Facebook",
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { onFacebookLoginClick() }
                        )

                        Spacer(modifier = Modifier.width(32.dp))

                        Image(
                            painter = painterResource(id = R.drawable.login_google),
                            contentDescription = "Google",
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { onGoogleLoginClick() }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

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
