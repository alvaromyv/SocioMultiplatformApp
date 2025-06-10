package com.amv.socioapp.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import com.amv.socioapp.navigation.AppNavHost
import com.amv.socioapp.ui.components.SocioNavegationWrapperUI
import com.amv.socioapp.ui.screens.LoginScreen
import com.amv.socioapp.ui.theme.SocioAppTheme
import com.amv.socioapp.ui.viewmodel.AuthViewModel
import com.amv.socioapp.ui.viewmodel.SociosViewModel
import com.amv.socioapp.ui.viewmodel.UsuariosViewModel
import io.github.vinceglb.filekit.coil.addPlatformFileSupport
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    onNavHostReady: suspend (NavController) -> Unit = {},
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
) {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components {
                addPlatformFileSupport()
            }
            .build()
    }

    SocioAppTheme {
        val navController = rememberNavController()
        val haySesionValida = authViewModel.sesionValida.collectAsState().value

        if(haySesionValida) {
            val sociosViewModel: SociosViewModel = viewModel(factory = SociosViewModel.Factory)
            val usuariosViewModel: UsuariosViewModel = viewModel(factory = UsuariosViewModel.Factory)
            SocioNavegationWrapperUI(navController, sociosViewModel, usuariosViewModel) {
                AppNavHost(navController, sociosViewModel, usuariosViewModel)
            }
            LaunchedEffect(navController) {
                onNavHostReady(navController)
            }
        } else {
            LoginScreen(authViewModel)
        }
    }
}