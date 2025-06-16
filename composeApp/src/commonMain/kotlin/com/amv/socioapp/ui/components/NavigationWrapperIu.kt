package com.amv.socioapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuite
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.amv.socioapp.data.SessionManager
import com.amv.socioapp.model.Rol
import com.amv.socioapp.navigation.Agrega
import com.amv.socioapp.navigation.Busqueda
import com.amv.socioapp.navigation.TopLevelDestination
import com.amv.socioapp.ui.events.MiCustomSnackbar
import com.amv.socioapp.ui.viewmodel.AuthUiState
import com.amv.socioapp.ui.viewmodel.AuthViewModel
import com.amv.socioapp.ui.viewmodel.SociosViewModel
import com.amv.socioapp.ui.viewmodel.UsuariosViewModel
import com.amv.socioapp.util.responsiveNavigationSuiteType
import sociomultiplatformapp.composeapp.generated.resources.Res
import sociomultiplatformapp.composeapp.generated.resources.actualizar
import sociomultiplatformapp.composeapp.generated.resources.agrega_titulo
import sociomultiplatformapp.composeapp.generated.resources.ajustes
import sociomultiplatformapp.composeapp.generated.resources.buscar
import com.hyperether.resources.stringResource

@Composable
fun SocioNavegationWrapperUI(
    navController: NavController,
    sociosViewModel: SociosViewModel,
    usuariosViewModel: UsuariosViewModel,
    authViewModel: AuthViewModel,
    snackbarHostState: SnackbarHostState,
    socioNavHost: @Composable () -> Unit = {}
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    var mostrarDialogoAjustes by rememberSaveable { mutableStateOf(false) }

    var esAdmin by mutableStateOf(false)

    when(val estado = authViewModel.authUiState) {
        is AuthUiState.Error -> SessionManager.cerrarSesion()
        is AuthUiState.Exception -> SessionManager.cerrarSesion()
        AuthUiState.Loading -> LoadingScreen()
        is AuthUiState.Success -> esAdmin = estado.sesion.usuario.rol == Rol.ADMINISTRADOR
    }


    if (mostrarDialogoAjustes) {
        AjustesDialog(
            onDismiss = { mostrarDialogoAjustes = false },
            onCerrarSesion = { SessionManager.cerrarSesion() },
            onReasignarNumeracion = { sociosViewModel.reasignarNumeracion(onSuccess = { usuariosViewModel.leerTodos() }) },
            esAdmin = esAdmin
        )
    }

    NavigationSuiteScaffoldFab(
        layoutType = responsiveNavigationSuiteType(hayNavegacion = esAdmin),
        navigationSuiteItems = {
                TopLevelDestination.entries
                    .filter { it.visibleNavigation }
                    .forEach { item ->
                        val isSelected = currentRoute == item.route
                        item(
                            icon = {
                                NavigationIcon(
                                    isSelected = isSelected,
                                    selectedIcon = item.selectedIcon,
                                    unselectedIcon = item.unselectedIcon,
                                    contentDescription = stringResource(item.title),
                                )
                            },
                            label = { Text(stringResource(item.title)) },
                            selected = isSelected,
                            onClick = { navController.navigate(item.route) }
                        )
                    }
        },
        floatingActionButton = {
            if(esAdmin) {
                FloatingActionButton(onClick = { navController.navigate(Agrega) }) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(Res.string.agrega_titulo))
                }
            }
        },
        extendedFloatingActionButton = {
            if(esAdmin) {
                ExtendedFloatingActionButton(
                    icon = {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(Res.string.agrega_titulo))
                    },
                    text = { Text(stringResource(Res.string.agrega_titulo)) },
                    onClick = { navController.navigate(Agrega) }
                )
            }
        }
    ) {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = {
                MiCustomSnackbar(snackbarHostState)
            },
        ) { padding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal,
                        ),
                    ),
            ) {
                val destination = TopLevelDestination.entries.find { currentRoute?.startsWith(it.route) == true }
                var shouldShowTopAppBar = false

                if (destination != null) {
                    shouldShowTopAppBar = true
                    val title = stringResource(destination.title)
                    SocioTopAppBar(
                        title = title,
                        navigationIcon = Icons.Filled.Search,
                        navigationIconContentDescription = stringResource(Res.string.buscar),
                        actualizarIcon = Icons.Filled.Refresh,
                        actualizarIconContentDescription = stringResource(Res.string.actualizar),
                        actionIcon = Icons.Filled.Settings,
                        actionIconContentDescription = stringResource(Res.string.ajustes),
                        onNavigationClick = { navController.navigate(Busqueda) },
                        onActualizarClick = { usuariosViewModel.leerTodos() },
                        onActionClick = { mostrarDialogoAjustes = true },
                        esAdmin = esAdmin
                    )
                }

                Box(
                    modifier = Modifier.consumeWindowInsets(
                        if (shouldShowTopAppBar) {
                            WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                        } else {
                            WindowInsets(0, 0, 0, 0)
                        },
                    ),
                ) {
                    socioNavHost()
                }
            }
        }
    }
}

@Composable
private fun NavigationIcon(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    contentDescription: String,
) {
    Icon(
        imageVector = if (isSelected) {
            selectedIcon
        } else {
            unselectedIcon
        },
        contentDescription = contentDescription
    )
}

@Composable
private fun NavigationSuiteScaffoldFab(
    navigationSuiteItems: NavigationSuiteScope.() -> Unit,
    modifier: Modifier = Modifier,
    layoutType: NavigationSuiteType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo()),
    navigationSuiteColors: NavigationSuiteColors = NavigationSuiteDefaults.colors(),
    containerColor: Color = NavigationSuiteScaffoldDefaults.containerColor,
    contentColor: Color = NavigationSuiteScaffoldDefaults.contentColor,
    floatingActionButton: @Composable () -> Unit = {},
    extendedFloatingActionButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    Surface(modifier = modifier, color = containerColor, contentColor = contentColor) {
        NavigationSuiteScaffoldLayout(
            navigationSuite = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (layoutType == NavigationSuiteType.NavigationRail || layoutType == NavigationSuiteType.NavigationDrawer) {
                        Box(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            if (layoutType == NavigationSuiteType.NavigationDrawer) { extendedFloatingActionButton() }
                            else floatingActionButton()
                        }
                    }
                    NavigationSuite(
                        layoutType = layoutType,
                        colors = navigationSuiteColors,
                        content = navigationSuiteItems
                    )
                }
            },
            layoutType = layoutType,
            content = {
                Scaffold(
                    modifier = Modifier.consumeWindowInsets(
                        when (layoutType) {
                            NavigationSuiteType.NavigationBar ->
                                NavigationBarDefaults.windowInsets.only(WindowInsetsSides.Bottom)
                            NavigationSuiteType.NavigationRail ->
                                NavigationRailDefaults.windowInsets.only(WindowInsetsSides.Start)
                            NavigationSuiteType.NavigationDrawer ->
                                DrawerDefaults.windowInsets.only(WindowInsetsSides.Start)
                            else -> WindowInsets(0, 0, 0, 0)
                        }
                    ),
                    floatingActionButton = {
                        if (layoutType == NavigationSuiteType.NavigationBar) { floatingActionButton() }
                    }
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        content()
                    }
                }
            }
        )
    }
}