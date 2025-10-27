package com.example.parcialtp3

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for the Parcial Template
 *
 * @HiltAndroidApp annotation triggers Hilt's code generation including:
 * - A base class for your application that serves as the application-level dependency container
 * - Component hierarchy that can inject dependencies throughout your app
 */
@HiltAndroidApp
class ParcialApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize app-level configurations here
        // Firebase, Analytics, etc.
    }
}
