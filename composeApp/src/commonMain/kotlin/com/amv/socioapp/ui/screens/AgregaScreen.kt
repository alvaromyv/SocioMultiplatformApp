package com.amv.socioapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amv.socioapp.ui.components.DetailPaneContent
import com.amv.socioapp.ui.components.MiButton
import com.amv.socioapp.ui.components.MiDialogoConfirmacion
import com.amv.socioapp.ui.viewmodel.InputViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AgregaScreen(
    viewModel: InputViewModel = remember { InputViewModel() }
) {
    DetailPaneContent(
        Modifier.fillMaxSize(),
        viewModel.construirUsuarioVacio(),
        viewModel,
        onEliminarClick = { },
        onEditarClick = { _, _ -> }
    ) { formValidado, onConfirmarClick, _, onEditarClick, onEliminarClick, onReiniciarClick, edicion ->
        OpcionesCreacion(
            formValidado,
            {  },
            { viewModel.reiniciarValores() }
        )
    }
}


@Composable
fun OpcionesCreacion(
    formValidado: Boolean,
    onConfirmarClick: () -> Unit = {},
    onReiniciarClcik: () -> Unit = {},
) {
    var abiertoDialogoConfirmar by remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        MiButton(
            accion = "Confirmar",
            onClick = { abiertoDialogoConfirmar = true },
            activado = formValidado,
            modifier = Modifier.weight(1f)
        )
        MiButton(
            accion = "Reiniciar",
            onClick = onReiniciarClcik,
            modifier = Modifier.weight(1f)
        )
    }

    if(abiertoDialogoConfirmar) {
        MiDialogoConfirmacion(
            icon = Icons.Filled.Check,
            titulo = "Confirmar",
            texto = "¿Estás seguro que deseas crear el usuario?",
            onConfirmar = { onConfirmarClick() },
            onRechazarRequest = { abiertoDialogoConfirmar = false },
        )
    }
}