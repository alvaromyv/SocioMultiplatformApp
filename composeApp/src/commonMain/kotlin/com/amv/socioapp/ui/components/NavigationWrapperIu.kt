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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.window.core.layout.WindowWidthSizeClass
import com.amv.socioapp.navigation.TopLevelDestination

@Composable
fun SocioNavegationWrapperUI(
    navController: NavController,
    socioNavHost: @Composable () -> Unit = {}
) {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val adaptiveWindowSizeClass = windowAdaptiveInfo.windowSizeClass

    val layoutType = when (adaptiveWindowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.EXPANDED -> NavigationSuiteType.NavigationRail
        WindowWidthSizeClass.MEDIUM -> NavigationSuiteType.NavigationRail
        WindowWidthSizeClass.COMPACT -> NavigationSuiteType.NavigationBar
        else -> NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(windowAdaptiveInfo)
    }

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationSuiteScaffoldFab(
        layoutType = layoutType,
        navigationSuiteItems = {
            TopLevelDestination.entries.forEach { item ->
                val isSelected = currentRoute == item.route
                item(
                    icon = {
                        NavigationIcon(
                            isSelected = isSelected,
                            selectedIcon = item.selectedIcon,
                            unselectedIcon = item.unselectedIcon,
                            contentDescription = item.label,
                        )
                    },
                    label = { Text(item.label) },
                    selected = isSelected,
                    onClick = { navController.navigate(item.route) }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = {
//                SnackbarHost(
//                    snackbarHostState,
//                    modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
//                )
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
                val destination = TopLevelDestination.entries.find { it.route == currentRoute }
                var shouldShowTopAppBar = false

                if (destination != null) {
                    shouldShowTopAppBar = true
                    SocioTopAppBar(
                        title = destination.label,
                        navigationIcon = Icons.Filled.Search,
                        navigationIconContentDescription = "Buscar",
                        actualizarIcon = Icons.Filled.Refresh,
                        actualizarIconContentDescription = "Actualizar",
                        actionIcon = Icons.Filled.Settings,
                        actionIconContentDescription = "Ajustes",
                        onNavigationClick = { },
                        onActualizarClick = { },
                        onActionClick = { }
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
                            floatingActionButton()
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
                        if (layoutType == NavigationSuiteType.NavigationBar) { floatingActionButton() } }
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        content()
                    }
                }
            }
        )
    }
}