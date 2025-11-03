package com.example.parcialtp3.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import com.example.parcialtp3.ui.theme.*
import com.example.parcialtp3.R
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText

class SpacedPasswordTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text
        val out = trimmed.map { "● " }.joinToString("")

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return offset * 2
            }

            override fun transformedToOriginal(offset: Int): Int {
                return offset / 2
            }
        }

        return TransformedText(AnnotatedString(out), offsetMapping)
    }
}

@Composable
fun SignupScreen(
    onSignUpSuccess: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainGreen)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 80.dp, bottom = 60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Create Account",
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
                        .padding(horizontal = 24.dp, vertical = 25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Full Name",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LettersAndIcons,
                            modifier = Modifier.padding(bottom = 2.dp, start = 16.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(42.dp)
                                .background(LightGreen, RoundedCornerShape(24.dp))
                                .border(0.dp, Color.Transparent, RoundedCornerShape(24.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            BasicTextField(
                                value = fullName,
                                onValueChange = { fullName = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                singleLine = true,
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontSize = 16.sp,
                                    color = DarkModeGreenBar
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                cursorBrush = SolidColor(MainGreen),
                                decorationBox = { innerTextField ->
                                    if (fullName.isEmpty()) {
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

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Email",
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
                                cursorBrush = SolidColor(MainGreen),
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

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Mobile Number",
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
                                value = mobileNumber,
                                onValueChange = { mobileNumber = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                singleLine = true,
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontSize = 16.sp,
                                    color = DarkModeGreenBar
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Phone,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                cursorBrush = SolidColor(MainGreen),
                                decorationBox = { innerTextField ->
                                    if (mobileNumber.isEmpty()) {
                                        Text(
                                            "+ 123 456 789",
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

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Date Of Birth",
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
                                value = dateOfBirth,
                                onValueChange = { dateOfBirth = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                singleLine = true,
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontSize = 16.sp,
                                    color = DarkModeGreenBar
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                cursorBrush = SolidColor(MainGreen),
                                decorationBox = { innerTextField ->
                                    if (dateOfBirth.isEmpty()) {
                                        Text(
                                            "DD / MM / YYY",
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
                                    .padding(horizontal = 16.dp, vertical = 0.dp)
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
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                cursorBrush = SolidColor(MainGreen),
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

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Confirm Password",
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
                                    .padding(horizontal = 16.dp, vertical = 0.dp)
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
                                        if (fullName.isNotBlank() && email.isNotBlank() &&
                                            mobileNumber.isNotBlank() && dateOfBirth.isNotBlank() &&
                                            password.isNotBlank() && confirmPassword.isNotBlank()) {
                                            onSignUpSuccess()
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

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "By continuing, you agree to",
                            fontSize = 11.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Terms of Use",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = LettersAndIcons,
                                modifier = Modifier.clickable { onTermsClick() }
                            )
                            Text(
                                text = " and ",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "Privacy Policy",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = LettersAndIcons,
                                modifier = Modifier.clickable { onPrivacyClick() }
                            )
                            Text(
                                text = ".",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (fullName.isNotBlank() && email.isNotBlank() &&
                                mobileNumber.isNotBlank() && dateOfBirth.isNotBlank() &&
                                password.isNotBlank() && confirmPassword.isNotBlank()) {
                                onSignUpSuccess()
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
                        enabled = fullName.isNotBlank() && email.isNotBlank() &&
                                  mobileNumber.isNotBlank() && dateOfBirth.isNotBlank() &&
                                  password.isNotBlank() && confirmPassword.isNotBlank()
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

                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Already have an account? ",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "Log In",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = BlueButton,
                            modifier = Modifier.clickable { onLoginClick() }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignupScreenPreview() {
    ParcialTP3Theme {
        SignupScreen()
    }
}
