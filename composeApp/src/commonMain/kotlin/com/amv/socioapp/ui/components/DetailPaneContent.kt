package com.amv.socioapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.amv.socioapp.model.Usuario
import com.amv.socioapp.network.model.SocioRequest
import com.amv.socioapp.network.model.UsuarioRequest
import com.amv.socioapp.ui.viewmodel.InputViewModel
import com.amv.socioapp.util.PerfilAvatar
import com.amv.socioapp.util.getLayoutType
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import com.hyperether.resources.stringResource
import sociomultiplatformapp.composeapp.generated.resources.*

@Composable
fun DetailPaneContent(
    modifier: Modifier = Modifier,
    usuario: Usuario,
    viewModel: InputViewModel = remember { InputViewModel() },
    onEliminarClick: (Int) -> Unit = {},
    onEditarClick: (UsuarioRequest?, SocioRequest?) -> Unit = { _, _ -> },
    edicion: Boolean,
    creacion: Boolean = false,
    onEdicionChange: (Boolean) -> Unit = {},
    opcionesContent: @Composable ColumnScope.(
        formValidado: Boolean,
        onConfirmarClick: () -> Unit,
        onCancelarClick: () -> Unit,
        onEditarClick: () -> Unit,
        onEliminarClick: () -> Unit,
        onReiniciarClick: () -> Unit,
        edicion: Boolean
    ) -> Unit = { formValidado, onConfirmarClick, onCancelarClick, onEditarClick, onEliminarClick, _, edicion ->
        OpcionesModificacion(
            formValidado = formValidado,
            onConfirmarClick = onConfirmarClick,
            onCancelarClick = onCancelarClick,
            onEditarClick = onEditarClick,
            onEliminarClick = onEliminarClick,
            edicion = edicion
        )
    }
) {
    LaunchedEffect(usuario.id, edicion) {
        viewModel.cargarUsuario(usuario)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                when (getLayoutType()) {
                    WindowWidthSizeClass.COMPACT -> DetailPaneContentForm(
                        Modifier.fillMaxWidth(),
                        viewModel,
                        usuario,
                        edicion,
                        false,
                        creacion
                    )

                    WindowWidthSizeClass.MEDIUM -> DetailPaneContentForm(
                        Modifier.fillMaxWidth(),
                        viewModel,
                        usuario,
                        edicion,
                        false,
                        creacion
                    )

                    WindowWidthSizeClass.EXPANDED -> DetailPaneContentForm(
                        Modifier.fillMaxWidth(),
                        viewModel,
                        usuario,
                        edicion,
                        true,
                        creacion
                    )
                }
            }
        }

        opcionesContent(
            viewModel.esFormularioValidado,
            { onEditarClick(viewModel.construirUsuario(), viewModel.construirSocio()) },
            { onEdicionChange(false); viewModel.cargarUsuario(usuario) },
            { onEdicionChange(true) },
            { onEliminarClick(usuario.id) },
            { viewModel.reiniciarValores() },
            edicion
        )
    }

}

@Composable
private fun DetailPaneContentForm(
    modifier: Modifier = Modifier,
    viewModel: InputViewModel,
    usuario: Usuario,
    edicion: Boolean = false,
    expanded: Boolean = false,
    creacion: Boolean = false
) {
    Column(
        modifier = modifier
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AvatarHeader(
            avatarUrl = viewModel.usuarioFormState.avatarUrl,
            avatarUri = viewModel.usuarioFormState.avatarUri,
            nombre = usuario.obtenerNombreCompleto(),
            onActualizarAvatarUri = viewModel::actualizarAvatarUri,
            edicion = edicion,
            expanded = expanded
        )

        HorizontalDivider()

        UsuarioFormContent(viewModel, edicion, creacion)

        if(viewModel.haySocio){
            SocioFormContent(viewModel, edicion)
        }
    }
}

@Composable
private fun AvatarHeader(
    avatarUrl: String?,
    avatarUri: PlatformFile?,
    nombre: String,
    onActualizarAvatarUri: (PlatformFile?) -> Unit,
    edicion: Boolean,
    expanded: Boolean,
) {
    val launcher = rememberFilePickerLauncher(
        type = FileKitType.Image
    ) { file -> onActualizarAvatarUri(file) }

    if (expanded) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PerfilAvatar(
                avatarUrl = avatarUrl,
                avatarUri = avatarUri,
                contentDescription = nombre,
                modifier = Modifier.size(144.dp),
                onClick = { if (edicion) launcher.launch() },
                contentAlignment = Alignment.CenterStart,
                iconSize = 144.dp
            )
            Text(
                text = nombre,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PerfilAvatar(
                avatarUrl = avatarUrl,
                avatarUri = avatarUri,
                contentDescription = nombre,
                modifier = Modifier.size(144.dp),
                onClick = { if (edicion) launcher.launch() },
                contentAlignment = Alignment.Center,
                iconSize = 144.dp
            )
            Text(
                text = nombre,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun UsuarioFormContent(
    viewModel: InputViewModel,
    edicion: Boolean,
    creacion: Boolean
) {
    MiLabel(
        stringResource(Res.string.informacion),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.fillMaxWidth()
    )

    if (edicion) {
        MiTextField(
            label = stringResource(Res.string.nombre),
            valor = viewModel.usuarioFormState.nombre,
            hayError = viewModel.esNombreErroneo,
            mensajeError = stringResource(Res.string.error_nombre),
            onValorChange = viewModel::actualizarNombre,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = Icons.Filled.Person,
            readOnly = !edicion
        )

        MiTextField(
            label = stringResource(Res.string.apellidos),
            valor = viewModel.usuarioFormState.apellidos ?: "",
            hayError = viewModel.esApellidosErroneo,
            mensajeError = stringResource(Res.string.error_apellidos),
            onValorChange = viewModel::actualizarApellidos,
            modifier = Modifier.fillMaxWidth(),
            readOnly = !edicion
        )

        MiTextField(
            label = stringResource(Res.string.telefono),
            valor = viewModel.usuarioFormState.telefono ?: "",
            hayError = viewModel.esTelefonoErroneo,
            mensajeError = stringResource(Res.string.error_telefono),
            onValorChange = viewModel::actualizarTelefono,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = Icons.Filled.Phone,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            readOnly = !edicion
        )
    }

    MiTextField(
        label = stringResource(Res.string.email),
        valor = viewModel.usuarioFormState.email,
        hayError = viewModel.esEmailErroneo,
        mensajeError = stringResource(Res.string.error_email),
        onValorChange = viewModel::actualizarEmail,
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = Icons.Filled.Email,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        readOnly = !edicion
    )

    if(creacion) {
        MiTextField(
            label = stringResource(Res.string.contraseña),
            valor = viewModel.usuarioFormState.password,
            hayError = viewModel.esPasswordErroneo,
            mensajeError = stringResource(Res.string.error_contraseña),
            onValorChange = viewModel::actualizarPassword,
            modifier = Modifier.fillMaxWidth(),
            esVisible = viewModel.esPasswordVisible,
            trailingIcon = Icons.Filled.Visibility,
            onTrailingIconClick = viewModel::actualizarPasswordVisible,
            enabled = !viewModel.esEmailErroneo,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )
    }

    SeleccionButtonRow(
        valor = viewModel.usuarioFormState.rol,
        label = stringResource(Res.string.rol),
        onValorChange = viewModel::actualizarRol,
        enabled = edicion,
        modifier = Modifier.fillMaxWidth()
    )

    if (edicion) {
        MiCheckBox(
            valor = viewModel.haySocio,
            label = stringResource(Res.string.es_socio),
            enabled = edicion,
            onValorChange = viewModel::actualizarHaySocio,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun SocioFormContent(
    viewModel: InputViewModel,
    edicion: Boolean
) {

    MiLabel(
        stringResource(Res.string.socio),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.fillMaxWidth()
    )

    SeleccionButtonRow(
        valor = viewModel.socioFormState.categoria,
        label = stringResource(Res.string.categoria),
        onValorChange = viewModel::actualizarCategoria,
        enabled = edicion,
        modifier = Modifier.fillMaxWidth()
    )

    MiTextFieldFecha(
        label = stringResource(Res.string.fecha_antiguedad),
        fecha = viewModel.socioFormState.fechaAntiguedad,
        hayError = viewModel.esFechaAntiguedadErroneo,

        onFechaChange = viewModel::actualizarFechaAntiguedad,
        modifier = Modifier.fillMaxWidth(),
        mensajeError = stringResource(Res.string.error_fecha),
        enabled = edicion
    )

    MiCheckBox(
        valor = viewModel.socioFormState.abonado,
        label = stringResource(Res.string.abonado),
        enabled = edicion,
        onValorChange = viewModel::actualizarAbonado,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun OpcionesModificacion(
    formValidado: Boolean,
    onConfirmarClick: () -> Unit = {},
    onEditarClick: () -> Unit = {},
    onEliminarClick: () -> Unit = {},
    onCancelarClick: () -> Unit = {},
    edicion: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        var abiertoDialogoEliminar by remember { mutableStateOf(false) }
        var abiertoDialogoConfirmar by remember { mutableStateOf(false) }

        if (edicion) {
            MiButton(
                accion = stringResource(Res.string.confirmar),
                onClick = { abiertoDialogoConfirmar = true },
                activado = formValidado,
                modifier = Modifier.weight(1f)
            )
            MiButton(
                accion = stringResource(Res.string.cancelar),
                onClick = onCancelarClick,
                modifier = Modifier.weight(1f)
            )
        } else {
            MiButton(
                accion = stringResource(Res.string.editar),
                onClick = onEditarClick,
                modifier = Modifier.weight(1f)
            )
            MiButton(
                accion = stringResource(Res.string.eliminar),
                onClick = { abiertoDialogoEliminar = true },
                modifier = Modifier.weight(1f)
            )
        }

        if (abiertoDialogoEliminar) {
            MiDialogoConfirmacion(
                icon = Icons.Filled.Delete,
                titulo = stringResource(Res.string.eliminar),
                texto = stringResource(Res.string.confirmar_eliminar),
                onConfirmar = { onEliminarClick() },
                onRechazarRequest = { abiertoDialogoEliminar = false },
            )
        }

        if (abiertoDialogoConfirmar) {
            MiDialogoConfirmacion(
                icon = Icons.Filled.Check,
                titulo = stringResource(Res.string.editar),
                texto = stringResource(Res.string.confirmar_modificar),
                onConfirmar = { onConfirmarClick() },
                onRechazarRequest = { abiertoDialogoConfirmar = false },
            )
        }
    }
}