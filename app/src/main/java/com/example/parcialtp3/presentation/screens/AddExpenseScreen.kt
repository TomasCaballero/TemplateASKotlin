package com.example.parcialtp3.presentation.screens

import android.app.Activity
import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.parcialtp3.R
import com.example.parcialtp3.domain.model.NavigationItem
import com.example.parcialtp3.presentation.components.BottomNavBar
import com.example.parcialtp3.presentation.components.NotificationButton
import com.example.parcialtp3.presentation.viewmodels.ExpenseViewModel
import com.example.parcialtp3.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddExpenseScreen(
    categoryName: String,
    onBackClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onNavigationItemSelected: (NavigationItem) -> Unit = {},
    onExpenseSaved: () -> Unit = {},
    viewModel: ExpenseViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(Calendar.getInstance().timeInMillis) }
    var showCategoryDropdown by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(categoryName) }

    val context = LocalContext.current
    val dateFormat = remember { SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()) }

    val saveSuccess by viewModel.saveSuccess.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = MainGreen.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    // Handle save success
    LaunchedEffect(saveSuccess) {
        if (saveSuccess) {
            viewModel.resetSaveSuccess()
            onExpenseSaved()
        }
    }

    // Category options
    val categories = listOf(
        "Food", "Transport", "Medicine", "Health", "Groceries",
        "Rent", "Entertainment", "Shopping", "Education", "Other"
    )

    Scaffold(
        containerColor = BackgroundGreenWhiteAndLetters,
        bottomBar = {
            BottomNavBar(
                selectedItem = NavigationItem.WALLET,
                onItemSelected = onNavigationItemSelected
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MainGreen)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Header
                AddExpenseHeader(
                    onBackClick = onBackClick,
                    onNotificationClick = onNotificationClick
                )

                // Form Content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            BackgroundGreenWhiteAndLetters,
                            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                        )
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp)
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    // Date Field
                    FormField(label = "Date") {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color(0xFFF0FFF4),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .clickable {
                                    val calendar = Calendar.getInstance()
                                    calendar.timeInMillis = selectedDate

                                    DatePickerDialog(
                                        context,
                                        { _, year, month, dayOfMonth ->
                                            calendar.set(year, month, dayOfMonth)
                                            selectedDate = calendar.timeInMillis
                                        },
                                        calendar.get(Calendar.YEAR),
                                        calendar.get(Calendar.MONTH),
                                        calendar.get(Calendar.DAY_OF_MONTH)
                                    ).show()
                                }
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = dateFormat.format(Date(selectedDate)),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = DarkModeGreenBar
                                )
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Select date",
                                    tint = Color(0xFF20C997)
                                )
                            }
                        }
                    }

                    // Category Field
                    FormField(label = "Category") {
                        Box {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Color(0xFFF0FFF4),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .clickable { showCategoryDropdown = true }
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = selectedCategory.ifEmpty { "Select the category" },
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = if (selectedCategory.isEmpty()) Color.Gray else DarkModeGreenBar
                                    )
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "Select category",
                                        tint = if (selectedCategory.isEmpty()) Color.Gray else Color(0xFF20C997)
                                    )
                                }
                            }

                            DropdownMenu(
                                expanded = showCategoryDropdown,
                                onDismissRequest = { showCategoryDropdown = false }
                            ) {
                                categories.forEach { category ->
                                    DropdownMenuItem(
                                        text = { Text(category) },
                                        onClick = {
                                            selectedCategory = category
                                            showCategoryDropdown = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Amount Field
                    FormField(label = "Amount") {
                        OutlinedTextField(
                            value = amount,
                            onValueChange = { amount = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("$0.00") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFF0FFF4),
                                unfocusedContainerColor = Color(0xFFF0FFF4),
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                    }

                    // Expense Title Field
                    FormField(label = "Expense Title") {
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Enter expense title") },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFF0FFF4),
                                unfocusedContainerColor = Color(0xFFF0FFF4),
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                    }

                    // Message Field
                    FormField(label = "Enter Message", labelColor = Color(0xFF20C997)) {
                        OutlinedTextField(
                            value = message,
                            onValueChange = { message = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            placeholder = { Text("Enter optional message") },
                            maxLines = 4,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFF0FFF4),
                                unfocusedContainerColor = Color(0xFFF0FFF4),
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                    }

                    // Error message
                    error?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                    }

                    // Save Button
                    Button(
                        onClick = {
                            val amountValue = amount.toDoubleOrNull()
                            if (title.isNotBlank() && amountValue != null && amountValue > 0) {
                                viewModel.saveExpense(
                                    categoryName = selectedCategory,
                                    title = title,
                                    amount = -amountValue, // Negative for expenses
                                    date = selectedDate,
                                    message = message.ifBlank { null }
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = !isLoading && title.isNotBlank() && amount.toDoubleOrNull() != null,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF20C997),
                            disabledContainerColor = Color(0xFF20C997).copy(alpha = 0.5f)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White
                            )
                        } else {
                            Text(
                                text = "Save",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AddExpenseHeader(
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = "Add Expenses",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        NotificationButton(onNotificationClick = onNotificationClick)
    }
}

@Composable
private fun FormField(
    label: String,
    labelColor: Color = DarkModeGreenBar,
    content: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = labelColor,
            modifier = Modifier.padding(start = 8.dp)
        )
        content()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddExpenseScreenPreview() {
    ParcialTP3Theme {
        AddExpenseScreen(categoryName = "Food")
    }
}
