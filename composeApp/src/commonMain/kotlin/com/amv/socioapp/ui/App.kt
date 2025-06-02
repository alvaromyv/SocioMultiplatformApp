package com.amv.socioapp.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.amv.socioapp.navigation.AppNavHost
import com.amv.socioapp.ui.components.SocioNavegationWrapperUI
import com.amv.socioapp.viewmodel.SociosViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    onNavHostReady: suspend (NavController) -> Unit = {},
    sociosViewModel: SociosViewModel = viewModel(factory = SociosViewModel.Factory),
) {
    MaterialTheme {
        val navController = rememberNavController()

        SocioNavegationWrapperUI(navController) {
            AppNavHost(
                navController,
                sociosViewModel
            )
        }

        LaunchedEffect(navController) {
            onNavHostReady(navController)
        }
    }
}