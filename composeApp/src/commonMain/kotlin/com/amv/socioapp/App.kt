package com.amv.socioapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.amv.socioapp.navigation.AppNavHost
import com.amv.socioapp.ui.components.menu.SocioNavegationWrapperUI
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App(
    onNavHostReady: suspend (NavController) -> Unit = {}
) {
    MaterialTheme {
        val navController = rememberNavController()

        SocioNavegationWrapperUI(navController) {
            AppNavHost(navController)
        }

        LaunchedEffect(navController) {
            onNavHostReady(navController)
        }
    }
}