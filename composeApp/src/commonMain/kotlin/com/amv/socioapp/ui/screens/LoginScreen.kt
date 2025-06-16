package com.amv.socioapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.amv.socioapp.network.model.AuthRequest
import com.amv.socioapp.ui.components.MiButton
import com.amv.socioapp.ui.components.MiLabel
import com.amv.socioapp.ui.components.MiTextField
import com.amv.socioapp.ui.events.MiCustomSnackbar
import com.amv.socioapp.ui.viewmodel.AuthViewModel
import com.amv.socioapp.ui.viewmodel.InputViewModel
import com.amv.socioapp.util.responsiveFillMaxWidth
import com.hyperether.resources.stringResource
import io.github.vinceglb.filekit.coil.AsyncImage
import org.jetbrains.compose.resources.painterResource
import sociomultiplatformapp.composeapp.generated.resources.*

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    inputViewModel: InputViewModel = remember { InputViewModel() },
    onLoginTry: (AuthRequest) -> Unit = { credenciales -> authViewModel.iniciarSesion(credenciales) },
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        snackbarHost = {
            MiCustomSnackbar(snackbarHostState)
        }
    ) { padding ->
        LoginContent(
            viewModel = inputViewModel,
            onLoginTry = onLoginTry,
            modifier = Modifier.padding(padding)
        )
    }

    /*when (currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.EXPANDED -> LoginContentExpanded(inputViewModel, onLoginTry)
        WindowWidthSizeClass.MEDIUM -> LoginContentMedium(inputViewModel, onLoginTry)
        WindowWidthSizeClass.COMPACT -> LoginContentCompact(inputViewModel, onLoginTry)
        else -> LoginContentCompact(inputViewModel, onLoginTry)
    }*/
}

@Composable
private fun LoginContent(
    viewModel: InputViewModel,
    onLoginTry: (AuthRequest) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.responsiveFillMaxWidth()) {
            Image(
                painter = painterResource(Res.drawable.logo_app),
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Fit,
                contentDescription = stringResource(Res.string.app_nombre),
            )
            ElevatedCard(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MiLabel(
                        text = stringResource(Res.string.iniciar_sesion),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    MiTextField(
                        label = stringResource(Res.string.email),
                        valor = viewModel.usuarioFormState.email,
                        hayError = viewModel.esEmailErroneo,
                        mensajeError = stringResource(Res.string.error_email),
                        onValorChange = viewModel::actualizarEmail,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    )

                    MiTextField(
                        label = stringResource(Res.string.contraseña),
                        valor = viewModel.usuarioFormState.password,
                        hayError = false,
                        mensajeError = "",
                        onValorChange = viewModel::actualizarPassword,
                        modifier = Modifier.fillMaxWidth(),
                        esVisible = viewModel.esPasswordVisible,
                        trailingIcon = Icons.Filled.Visibility,
                        onTrailingIconClick = viewModel::actualizarPasswordVisible,
                        enabled = !viewModel.esEmailErroneo,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    )

                    MiButton(
                        accion = stringResource(Res.string.acceder),
                        onClick = {
                            onLoginTry(
                                AuthRequest(
                                    viewModel.usuarioFormState.email,
                                    viewModel.usuarioFormState.password
                                )
                            )
                        },
                        activado = viewModel.esFormularioLoginValido,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}