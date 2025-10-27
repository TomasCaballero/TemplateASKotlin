package com.example.parcialtp3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.parcialtp3.navigation.NavGraph
import com.example.parcialtp3.ui.theme.ParcialTP3Theme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity for the Parcial Template
 *
 * @AndroidEntryPoint annotation enables Hilt dependency injection in this Activity
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParcialTP3Theme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}