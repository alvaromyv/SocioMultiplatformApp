package com.amv.socioapp.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import com.amv.socioapp.data.AjustesManager
import com.amv.socioapp.navigation.AppNavHost
import com.amv.socioapp.ui.components.SocioNavegationWrapperUI
import com.amv.socioapp.ui.events.ObserveAsEvents
import com.amv.socioapp.ui.events.SnackbarController
import com.amv.socioapp.ui.events.SnackbarVisualsCustom
import com.amv.socioapp.ui.screens.LoginScreen
import com.amv.socioapp.ui.theme.SocioAppTheme
import com.amv.socioapp.ui.viewmodel.AuthViewModel
import com.amv.socioapp.ui.viewmodel.SociosViewModel
import com.amv.socioapp.ui.viewmodel.UsuariosViewModel
import com.hyperether.resources.currentLanguage
import io.github.vinceglb.filekit.coil.addPlatformFileSupport
import io.ktor.websocket.Frame.Text
import kotlinx.coroutines.launch

@Composable
fun App(
    onNavHostReady: suspend (NavController) -> Unit = {},
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory),
) {
    val lang by currentLanguage
    Text(text = lang.displayName.toString())

    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components {
                addPlatformFileSupport()
            }
            .build()
    }

    val scope = rememberCoroutineScope()
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(
        flow = SnackbarController.events,
        snackbarHostState
    ) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val visuals = SnackbarVisualsCustom(
                message = event.message,
                actionLabel = event.action?.name,
                withDismissAction = true,
                duration = SnackbarDuration.Short,
                type = event.type
            )

            val result = snackbarHostState.showSnackbar(visuals)

            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    SocioAppTheme(
        appTheme = AjustesManager.tema
    ) {
        val haySesionValida = authViewModel.sesionValida.collectAsState().value

        key(haySesionValida) {
            if (haySesionValida) {
                val navController = rememberNavController()
                val sociosViewModel: SociosViewModel = viewModel(factory = SociosViewModel.Factory)
                val usuariosViewModel: UsuariosViewModel =
                    viewModel(factory = UsuariosViewModel.Factory)
                SocioNavegationWrapperUI(
                    navController,
                    sociosViewModel,
                    usuariosViewModel,
                    authViewModel,
                    snackbarHostState
                ) {
                    AppNavHost(navController, authViewModel, sociosViewModel, usuariosViewModel)
                }
                LaunchedEffect(navController) {
                    onNavHostReady(navController)
                }
            } else {
                LoginScreen(authViewModel, snackbarHostState = snackbarHostState)
            }
        }
    }

//        if(haySesionValida) {
//            val sociosViewModel: SociosViewModel = viewModel(factory = SociosViewModel.Factory)
//            val usuariosViewModel: UsuariosViewModel = viewModel(factory = UsuariosViewModel.Factory)
//            SocioNavegationWrapperUI(navController, sociosViewModel, usuariosViewModel, authViewModel, snackbarHostState) {
//                AppNavHost(navController, authViewModel, sociosViewModel, usuariosViewModel)
//            }
//            LaunchedEffect(navController) {
//                onNavHostReady(navController)
//            }
//        } else {
//            LoginScreen(authViewModel, snackbarHostState = snackbarHostState)
//        }
}