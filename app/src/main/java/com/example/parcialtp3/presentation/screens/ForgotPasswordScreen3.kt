package com.example.parcialtp3.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.parcialtp3.R
import com.example.parcialtp3.ui.theme.*

@Composable
fun ForgotPasswordScreen3(
    onChangePasswordClick: (String, String) -> Unit = { _, _ -> }
) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainGreen)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 80.dp, bottom = 60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "New Password",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = LettersAndIcons
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillParentMaxHeight()
                        .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                        .background(BackgroundGreenWhiteAndLetters)
                        .padding(horizontal = 24.dp, vertical = 40.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "New Password",
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
                                value = newPassword,
                                onValueChange = { newPassword = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .padding(end = 32.dp),
                                singleLine = true,
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontSize = 20.sp,
                                    color = DarkModeGreenBar
                                ),
                                visualTransformation = if (newPasswordVisible) {
                                    VisualTransformation.None
                                } else {
                                    SpacedPasswordTransformation()
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Password,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                cursorBrush = SolidColor(MainGreen),
                                decorationBox = { innerTextField ->
                                    if (newPassword.isEmpty()) {
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
                                onClick = { newPasswordVisible = !newPasswordVisible },
                                modifier = Modifier.align(Alignment.CenterEnd)
                            ) {
                                Image(
                                    painter = painterResource(
                                        id = if (newPasswordVisible) R.drawable.eye_pass_open else R.drawable.eye_pass
                                    ),
                                    contentDescription = if (newPasswordVisible) "Hide password" else "Show password",
                                    modifier = Modifier.size(20.dp),
                                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(DarkModeGreenBar)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Confirm New Password",
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
                                value = confirmPassword,
                                onValueChange = { confirmPassword = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .padding(end = 32.dp),
                                singleLine = true,
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontSize = 20.sp,
                                    color = DarkModeGreenBar
                                ),
                                visualTransformation = if (confirmPasswordVisible) {
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
                                        if (newPassword.isNotBlank() && confirmPassword.isNotBlank()) {
                                            onChangePasswordClick(newPassword, confirmPassword)
                                        }
                                    }
                                ),
                                cursorBrush = SolidColor(MainGreen),
                                decorationBox = { innerTextField ->
                                    if (confirmPassword.isEmpty()) {
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
                                onClick = { confirmPasswordVisible = !confirmPasswordVisible },
                                modifier = Modifier.align(Alignment.CenterEnd)
                            ) {
                                Image(
                                    painter = painterResource(
                                        id = if (confirmPasswordVisible) R.drawable.eye_pass_open else R.drawable.eye_pass
                                    ),
                                    contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password",
                                    modifier = Modifier.size(20.dp),
                                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(DarkModeGreenBar)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(80.dp))

                    Button(
                        onClick = {
                            if (newPassword.isNotBlank() && confirmPassword.isNotBlank()) {
                                onChangePasswordClick(newPassword, confirmPassword)
                            }
                        },
                        modifier = Modifier
                            .width(220.dp)
                            .height(48.dp)
                            .align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MainGreen,
                            contentColor = Color.White,
                            disabledContainerColor = MainGreen.copy(alpha = 0.6f),
                            disabledContentColor = Color.White.copy(alpha = 0.6f)
                        ),
                        shape = RoundedCornerShape(24.dp),
                        enabled = newPassword.isNotBlank() && confirmPassword.isNotBlank()
                    ) {
                        Text(
                            text = "Change Password",
                            fontSize = 20.sp,
                            color = LettersAndIcons,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ForgotPasswordScreen3Preview() {
    ForgotPasswordScreen3()
}
