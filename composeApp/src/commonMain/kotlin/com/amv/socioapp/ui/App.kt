package com.amv.socioapp.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.amv.socioapp.navigation.AppNavHost
import com.amv.socioapp.network.NetworkUtils
import com.amv.socioapp.ui.components.SocioNavegationWrapperUI
import com.amv.socioapp.ui.theme.SocioAppTheme
import com.amv.socioapp.viewmodel.SociosViewModel
import okio.FileSystem
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    onNavHostReady: suspend (NavController) -> Unit = {},
    sociosViewModel: SociosViewModel = viewModel(factory = SociosViewModel.Factory),
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