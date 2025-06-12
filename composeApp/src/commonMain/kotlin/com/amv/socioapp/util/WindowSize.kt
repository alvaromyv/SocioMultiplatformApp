package com.amv.socioapp.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.window.core.layout.WindowWidthSizeClass

@Composable
fun Modifier.responsiveLayout(windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()): Modifier {
    val adaptiveWindowSizeClass = windowAdaptiveInfo.windowSizeClass

    return when (adaptiveWindowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> this.fillMaxWidth(0.9f).scale(0.9f)
        WindowWidthSizeClass.MEDIUM -> this.fillMaxWidth(0.75f).scale(1f)
        WindowWidthSizeClass.EXPANDED -> this.fillMaxWidth(0.6f).scale(1.10f)
        else -> this.fillMaxWidth().scale(1f)
    }
}

@Composable
fun responsiveNavigationSuiteType(windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()): NavigationSuiteType {
    val adaptiveWindowSizeClass = windowAdaptiveInfo.windowSizeClass

    return when (adaptiveWindowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.EXPANDED -> NavigationSuiteType.NavigationDrawer
        WindowWidthSizeClass.MEDIUM -> NavigationSuiteType.NavigationRail
        WindowWidthSizeClass.COMPACT -> NavigationSuiteType.NavigationBar
        else -> NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(windowAdaptiveInfo)
    }
}