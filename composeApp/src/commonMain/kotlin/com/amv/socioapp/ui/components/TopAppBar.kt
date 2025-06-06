package com.amv.socioapp.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocioTopAppBar(
    title: String,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String,
    actualizarIcon: ImageVector,
    actualizarIconContentDescription: String,
    actionIcon: ImageVector,
    actionIconContentDescription: String,
    onNavigationClick: () -> Unit = {},
    onActualizarClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        actions = {
            IconButton(onClick = onActualizarClick) {
                Icon(
                    imageVector = actualizarIcon,
                    contentDescription = actualizarIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = actionIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
    )
}