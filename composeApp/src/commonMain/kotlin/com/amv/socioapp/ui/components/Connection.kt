package com.amv.socioapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NetworkLocked
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hyperether.resources.stringResource
import sociomultiplatformapp.composeapp.generated.resources.*

@Composable
fun LoadingScreen() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.width(128.dp))
    }
}

@Composable
fun ExceptionScreen(onReintentarClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.NetworkLocked,
            contentDescription = stringResource(Res.string.error_red),
            modifier = Modifier.width(128.dp)
        )
        Text(text = stringResource(Res.string.error_red), modifier = Modifier.padding(16.dp))
        MiButton(accion = stringResource(Res.string.reintentar), onClick = onReintentarClick, modifier = Modifier.wrapContentSize())
    }
}