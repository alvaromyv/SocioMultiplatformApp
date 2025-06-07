package com.amv.socioapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.amv.socioapp.navigation.AppNavHost
import com.amv.socioapp.ui.components.SocioNavegationWrapperUI
import com.amv.socioapp.ui.theme.SocioAppTheme
import com.amv.socioapp.ui.viewmodel.AuthViewModel
import com.amv.socioapp.ui.viewmodel.SociosViewModel
import com.amv.socioapp.ui.viewmodel.UsuariosViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    onNavHostReady: suspend (NavController) -> Unit = {},
    sociosViewModel: SociosViewModel = viewModel(factory = SociosViewModel.Factory),
    usuariosViewModel: UsuariosViewModel = viewModel(factory = UsuariosViewModel.Factory),
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
) {
    SocioAppTheme {
        val navController = rememberNavController()

        SocioNavegationWrapperUI(navController) {
            AppNavHost(navController, sociosViewModel)
        }

        LaunchedEffect(navController) {
            onNavHostReady(navController)
        }
    }
}