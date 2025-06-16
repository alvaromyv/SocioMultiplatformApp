package com.amv.socioapp.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.amv.socioapp.network.model.SocioRequest
import com.amv.socioapp.network.model.UsuarioRequest
import com.amv.socioapp.ui.components.DetailPaneContent
import com.amv.socioapp.ui.components.ExceptionScreen
import com.amv.socioapp.ui.components.LoadingScreen
import com.amv.socioapp.ui.viewmodel.AuthUiState

@Composable
fun PerfilScreen(
    modifier: Modifier = Modifier,
    authUiState: AuthUiState,
    onEliminarClick: (Int) -> Unit = {},
    onEditarClick: (UsuarioRequest?, SocioRequest?) -> Unit = { _, _ -> },
    onReintentarClick: () -> Unit = {}
) {
    when (val estado = authUiState) {
        is AuthUiState.Success -> {
            DetailPaneContent(
                modifier = modifier.fillMaxWidth(),
                usuario = estado.sesion.usuario,
                onEliminarClick = onEliminarClick,
                onEditarClick = onEditarClick,
                edicion = false
            ) { formValidado, onConfirmarClick, _, onEditarClick, onEliminarClick, onReiniciarClick, edicion ->

            }
        }
        is AuthUiState.Error -> {}
        is AuthUiState.Exception -> ExceptionScreen(onReintentarClick = onReintentarClick)
        AuthUiState.Loading -> LoadingScreen()
    }
}