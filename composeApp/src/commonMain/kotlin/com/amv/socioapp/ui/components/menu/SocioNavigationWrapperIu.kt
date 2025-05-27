package com.amv.socioapp.ui.components.menu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
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
        WindowWidthSizeClass.EXPANDED -> NavigationSuiteType.NavigationDrawer
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
