package com.amv.socioapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.amv.socioapp.navigation.AppNavHost
import com.amv.socioapp.ui.SocioNavegationWrapperUI
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