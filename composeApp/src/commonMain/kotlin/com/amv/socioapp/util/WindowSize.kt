package com.amv.socioapp.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.window.core.layout.WindowWidthSizeClass

@Composable
fun Modifier.responsiveFillMaxWidth(windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()): Modifier {
    return when (getLayoutType(windowAdaptiveInfo)) {
        WindowWidthSizeClass.COMPACT -> this.fillMaxWidth(0.8f)
        WindowWidthSizeClass.MEDIUM -> this.fillMaxWidth(0.6f)
        WindowWidthSizeClass.EXPANDED -> this.fillMaxWidth(0.25f)
        else -> this.fillMaxWidth()
    }
}

@Composable
fun responsiveNavigationSuiteType(windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(), hayNavegacion: Boolean = true): NavigationSuiteType {
    return if(hayNavegacion) when (getLayoutType(windowAdaptiveInfo)) {
        WindowWidthSizeClass.EXPANDED -> NavigationSuiteType.NavigationDrawer
        WindowWidthSizeClass.MEDIUM -> NavigationSuiteType.NavigationRail
        WindowWidthSizeClass.COMPACT -> NavigationSuiteType.NavigationBar
        else -> NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(windowAdaptiveInfo)
    } else NavigationSuiteType.None
}

@Composable
fun getLayoutType(windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()): WindowWidthSizeClass {
    return windowAdaptiveInfo.windowSizeClass.windowWidthSizeClass
}