package com.example.parcialtp3.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcialtp3.R
import com.example.parcialtp3.ui.theme.*

@Composable
fun ForgotPasswordScreen2(
    onAcceptClick: (String) -> Unit = {},
    onSendAgainClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onFacebookClick: () -> Unit = {},
    onGoogleClick: () -> Unit = {}
) {
    var pin by remember { mutableStateOf("273916") }

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
                        text = "Security Pin",
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
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Enter Security Pin",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = LettersAndIcons,
                        modifier = Modifier.padding(bottom = 40.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 40.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        pin.take(6).forEachIndexed { index, char ->
                            Box(
                                modifier = Modifier
                                    .size(35.dp)
                                    .border(2.dp, MainGreen, CircleShape)
                                    .background(BackgroundGreenWhiteAndLetters, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = char.toString(),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = DarkModeGreenBar
                                )
                            }
                        }
                    }

                    Button(
                        onClick = { onAcceptClick(pin) },
                        modifier = Modifier
                            .width(220.dp)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MainGreen,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = "Accept",
                            fontSize = 20.sp,
                            color = LettersAndIcons,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(
                        onClick = onSendAgainClick,
                        modifier = Modifier
                            .width(220.dp)
                            .height(48.dp)
                    ) {
                        Text(
                            text = "Send Again",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MainGreen,
                            letterSpacing = 0.5.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = "or sign up with",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
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
                                .clickable { onFacebookClick() }
                        )

                        Spacer(modifier = Modifier.width(32.dp))

                        Image(
                            painter = painterResource(id = R.drawable.login_google),
                            contentDescription = "Google",
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { onGoogleClick() }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
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
                            fontWeight = FontWeight.Normal,
                            color = BlueButton,
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
fun ForgotPasswordScreen2Preview() {
    ForgotPasswordScreen2()
}
