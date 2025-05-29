package com.amv.socioapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuite
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
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
import com.amv.socioapp.navigation.Contabilidad
import com.amv.socioapp.navigation.Inicio
import com.amv.socioapp.navigation.Socios
import com.amv.socioapp.navigation.getSerialName

private data class NavItem<T>(
    val name: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val route: T
)

@Composable
fun SocioNavegationWrapperUI(
    navController: NavController,
    contenido: @Composable () -> Unit = {}
) {
    val windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
    val adaptiveWindowSizeClass = windowAdaptiveInfo.windowSizeClass
    val widthSizeClass = adaptiveWindowSizeClass.windowWidthSizeClass

    val layoutType = when (widthSizeClass) {
        WindowWidthSizeClass.EXPANDED -> NavigationSuiteType.NavigationRail
        WindowWidthSizeClass.MEDIUM -> NavigationSuiteType.NavigationRail
        WindowWidthSizeClass.COMPACT -> NavigationSuiteType.NavigationBar
        else -> NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(windowAdaptiveInfo)
    }

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val items = listOf(
        NavItem(
            name = "Inicio",
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
            route = getSerialName<Inicio>()
        ),
        NavItem(
            name = "Socios",
            selectedIcon = Icons.Filled.Person,
            unSelectedIcon = Icons.Outlined.Person,
            route = getSerialName<Socios>()
        ),
        NavItem(
            name = "Contabilidad",
            selectedIcon = Icons.Filled.AccountBalance,
            unSelectedIcon = Icons.Outlined.AccountBalance,
            route = getSerialName<Contabilidad>()
        )

    )

    NavigationSuiteScaffold(
        layoutType = layoutType,
        navigationSuiteItems = {
            items.forEach { item ->
                val isSelected = currentRoute == item.route
                item(
                    icon = {
                        BarIconView(
                            isSelected = isSelected,
                            selectedIcon = item.selectedIcon,
                            unselectedIcon = item.unSelectedIcon,
                            contentDescription = item.name,
                        )
                    },
                    label = { Text(item.name) },
                    selected = isSelected,
                    onClick = { navController.navigate(item.route) }
                )
            }
        }
    ) {
        contenido()
    }
}

@Composable
private fun BarIconView(
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

//@Composable
//private fun NavigationSuiteScaffoldFab(
//    navigationSuiteItems: NavigationSuiteScope.() -> Unit,
//    modifier: Modifier = Modifier,
//    layoutType: NavigationSuiteType =
//        NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo()),
//    navigationSuiteColors: NavigationSuiteColors = NavigationSuiteDefaults.colors(),
//    containerColor: Color = NavigationSuiteScaffoldDefaults.containerColor,
//    contentColor: Color = NavigationSuiteScaffoldDefaults.contentColor,
//    floatingActionButton: @Composable () -> Unit = {},
//    content: @Composable () -> Unit = {},
//) {
//    Surface(modifier = modifier, color = containerColor, contentColor = contentColor) {
//        NavigationSuiteScaffoldLayout(
//            navigationSuite = {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    if (layoutType == NavigationSuiteType.NavigationRail || layoutType == NavigationSuiteType.NavigationDrawer) {
//                        Box(
//                            modifier = Modifier.padding(16.dp)
//                        ) {
//                            floatingActionButton()
//                        }
//                    }
//                    NavigationSuite(
//                        layoutType = layoutType,
//                        colors = navigationSuiteColors,
//                        content = navigationSuiteItems
//                    )
//                }
//            },
//            layoutType = layoutType,
//            content = {
//                Scaffold(
//                    modifier = Modifier.consumeWindowInsets(
//                        when (layoutType) {
//                            NavigationSuiteType.NavigationBar ->
//                                NavigationBarDefaults.windowInsets.only(WindowInsetsSides.Bottom)
//                            NavigationSuiteType.NavigationRail ->
//                                NavigationRailDefaults.windowInsets.only(WindowInsetsSides.Start)
//                            NavigationSuiteType.NavigationDrawer ->
//                                DrawerDefaults.windowInsets.only(WindowInsetsSides.Start)
//                            else -> WindowInsets(0, 0, 0, 0)
//                        }
//                    ),
//                    floatingActionButton = {
//                        if (layoutType == NavigationSuiteType.NavigationBar) { floatingActionButton() } }
//                ) {
//                    Box(modifier = Modifier.padding(it)) {
//                        content()
//                    }
//                }
//            }
//        )
//    }
//}

//    NavigationSuiteScaffoldFab(
//        navigationSuiteItems = {
//            items.forEach { item ->
//                val isSelected = currentRoute == item.route
//                item(
//                    icon = {
//                        BarIconView(
//                            isSelected = isSelected,
//                            selectedIcon = item.selectedIcon,
//                            unselectedIcon = item.unSelectedIcon,
//                            contentDescription = item.name,
//                        )
//                    },
//                    label = { Text(item.name) },
//                    selected = isSelected,
//                    onClick = { navController.navigate(item.route) }
//                )
//            }
//        },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {}
//            ) {
//                Icon(Icons.Filled.Add, "")
//            }
//        }
//    ) {
//        contenido()
//    }