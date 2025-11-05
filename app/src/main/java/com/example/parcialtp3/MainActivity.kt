package com.example.parcialtp3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.example.parcialtp3.navigation.NavGraph
import com.example.parcialtp3.presentation.viewmodels.ThemeViewModel
import com.example.parcialtp3.ui.theme.ParcialTP3Theme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity for the Parcial Template
 *
 * @AndroidEntryPoint annotation enables Hilt dependency injection in this Activity
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            ParcialTP3Theme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}