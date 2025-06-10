package com.amv.socioapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.amv.socioapp.network.model.AuthRequest
import com.amv.socioapp.ui.components.MiButton
import com.amv.socioapp.ui.components.MiLabel
import com.amv.socioapp.ui.components.MiTextField
import com.amv.socioapp.ui.viewmodel.AuthViewModel
import com.amv.socioapp.ui.viewmodel.InputViewModel

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    inputViewModel: InputViewModel = remember { InputViewModel() },
    onLoginTry: (AuthRequest) -> Unit = { credenciales -> authViewModel.iniciarSesion(credenciales) }
) {
    LoginContent(
        inputViewModel = inputViewModel,
        onLoginTry = onLoginTry,
    )
    /*when (currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.EXPANDED -> LoginContentExpanded(inputViewModel, onLoginTry)
        WindowWidthSizeClass.MEDIUM -> LoginContentMedium(inputViewModel, onLoginTry)
        WindowWidthSizeClass.COMPACT -> LoginContentCompact(inputViewModel, onLoginTry)
        else -> LoginContentCompact(inputViewModel, onLoginTry)
    }*/
}

@Composable
private fun LoginContent(
    inputViewModel: InputViewModel,
    onLoginTry: (AuthRequest) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ElevatedCard(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            modifier = Modifier.wrapContentSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MiLabel(
                    text = "INICIAR SESIÓN",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                MiTextField(
                    label = "Email",
                    valor = inputViewModel.usuarioFormState.email,
                    hayError = inputViewModel.esEmailErroneo,
                    mensajeError = "El email introducido no es válido.",
                    onValorChange = inputViewModel::actualizarEmail,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                )

                MiTextField(
                    label = "Contraseña",
                    valor = inputViewModel.usuarioFormState.password,
                    hayError = false,
                    mensajeError = "",
                    onValorChange = inputViewModel::actualizarPassword,
                    modifier = Modifier.fillMaxWidth(),
                    esVisible = inputViewModel.esPasswordVisible,
                    trailingIcon = Icons.Filled.Visibility,
                    onTrailingIconClick = inputViewModel::actualizarPasswordVisible,
                    readOnly = !inputViewModel.esEmailErroneo,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )

                MiButton(
                    accion = "Acceder",
                    onClick = {
                        onLoginTry(
                            AuthRequest(
                                inputViewModel.usuarioFormState.email,
                                inputViewModel.usuarioFormState.password
                            )
                        )
                    },
                    activado = inputViewModel.esFormularioLoginValido,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}