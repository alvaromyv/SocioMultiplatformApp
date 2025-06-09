package com.amv.socioapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.amv.socioapp.ui.App
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.manualFileKitCoreInitialization

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        AppEnvironment.setupAppEnvironment()
        FileKit.manualFileKitCoreInitialization(this)
        setContent {
            App()
        }
    }
}