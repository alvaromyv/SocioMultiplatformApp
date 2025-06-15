package com.amv.socioapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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

@Composable
fun DetailPaneContent(
    modifier: Modifier = Modifier,
    usuario: Usuario,
    viewModel: InputViewModel = remember { InputViewModel() },
    onEliminarClick: (Int) -> Unit = {},
    onEditarClick: (UsuarioRequest?, SocioRequest?) -> Unit = { _, _ -> },
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
    LaunchedEffect(Unit) {
        viewModel.actualizarHaySocio(usuario.socio != null)
        viewModel.cargarUsuario(usuario)
    }

    var edicion by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ElevatedCard(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            when (getLayoutType()) {
                WindowWidthSizeClass.COMPACT -> DetailPaneContentCompactMedium(Modifier.fillMaxWidth(), viewModel, usuario, edicion)
                WindowWidthSizeClass.MEDIUM -> DetailPaneContentCompactMedium(Modifier.fillMaxWidth(), viewModel, usuario, edicion)
                WindowWidthSizeClass.EXPANDED -> DetailPaneContentExpanded(Modifier.fillMaxWidth(), viewModel, usuario, edicion)
            }
        }
        opcionesContent(
            viewModel.esFormularioValidado, // FormValidado
            { onEditarClick(viewModel.construirUsuario(), viewModel.construirSocio()) }, // onConfirmarClick
            { edicion = false; viewModel.cargarUsuario(usuario) }, // onCancelarClick
            { edicion = true }, // onEditarClick
            { onEliminarClick(usuario.id) }, // onEliminarClick
            { viewModel.reiniciarValores() }, // onReiniciarClick
            edicion
        )
    }
}

@Composable
private fun DetailPaneContentCompactMedium(
    modifier: Modifier = Modifier,
    vm: InputViewModel,
    usuario: Usuario,
    edicion: Boolean = false
) {
    Column(
        modifier = modifier
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AvatarHeader(
            avatarUrl = vm.usuarioFormState.avatarUrl,
            avatarUri = vm.usuarioFormState.avatarUri,
            nombre = usuario.obtenerNombreCompleto(),
            onActualizarAvatarUri = vm::actualizarAvatarUri,
            edicion = edicion,
            expanded = false
        )

        if(edicion) {
            MiTextField(
                label = "Nombre",
                valor = vm.usuarioFormState.nombre,
                hayError = vm.esNombreErroneo,
                mensajeError = "El nombre no debe superar los ${InputViewModel.MAX_STRING_LENGTH} caracteres.",
                onValorChange = vm::actualizarNombre,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = Icons.Filled.Person,
                readOnly = !edicion
            )

            MiTextField(
                label = "Apellidos",
                valor = vm.usuarioFormState.apellidos ?: "",
                hayError = vm.esApellidosErroneo,
                mensajeError = "El apellido no debe superar los ${InputViewModel.MAX_STRING_LENGTH} caracteres.}",
                onValorChange = vm::actualizarApellidos,
                modifier = Modifier.fillMaxWidth(),
                readOnly = !edicion
            )
        }

        if(!edicion && vm.usuarioFormState.telefono != null) {
            MiTextField(
                label = "Telefono",
                valor = vm.usuarioFormState.telefono ?: "",
                hayError = vm.esTelefonoErroneo,
                mensajeError = "El telefono introducido no es válido.",
                onValorChange = vm::actualizarTelefono,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = Icons.Filled.Phone,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                readOnly = !edicion
            )
        }

        MiTextField(
            label = "Email",
            valor = vm.usuarioFormState.email,
            hayError = vm.esEmailErroneo,
            mensajeError = "El correo introducido no es válido.",
            onValorChange = vm::actualizarEmail,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = Icons.Filled.Email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            readOnly = !edicion
        )

        SeleccionButtonRow(
            valor = vm.usuarioFormState.rol,
            onValorChange = vm::actualizarRol,
            enabled = edicion,
            modifier = Modifier.fillMaxWidth()
        )

        if(edicion) {
            MiCheckBox(
                valor = vm.haySocio,
                label = "¿Es socio?",
                enabled = edicion,
                onValorChange = vm::actualizarHaySocio,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (vm.haySocio){
            MiLabel("SOCIO", textAlign = TextAlign.Center, style = MaterialTheme.typography.headlineSmall, modifier = Modifier.fillMaxWidth())

            SeleccionButtonRow(
                valor = vm.socioFormState.categoria,
                onValorChange = vm::actualizarCategoria,
                enabled = edicion,
                modifier = Modifier.fillMaxWidth()
            )

            MiTextFieldFecha(
                label = "Fecha de antiguedad",
                fecha = vm.socioFormState.fechaAntiguedad,
                hayError = vm.esFechaAntiguedadErroneo,

                onFechaChange = vm::actualizarFechaAntiguedad,
                modifier = Modifier.fillMaxWidth(),
                mensajeError = "La fecha introducida no debe ser posterior a la actual.",
                enabled = edicion
            )

            MiCheckBox(
                valor = vm.socioFormState.abonado,
                label = "Abonado",
                enabled = edicion,
                onValorChange = vm::actualizarAbonado,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun DetailPaneContentExpanded(
    modifier: Modifier = Modifier,
    vm: InputViewModel,
    usuario: Usuario,
    edicion: Boolean = false
) {
    Column (
        modifier = modifier
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AvatarHeader(
            avatarUrl = vm.usuarioFormState.avatarUrl,
            avatarUri = vm.usuarioFormState.avatarUri,
            nombre = usuario.obtenerNombreCompleto(),
            onActualizarAvatarUri = vm::actualizarAvatarUri,
            edicion = edicion,
            expanded = true
        )
        Row {
            Column(modifier = Modifier.weight(1f)) {

            }

            Column(modifier = Modifier.weight(1f)) {

            }
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

        if(edicion) {
            MiButton(
                accion = "Confirmar",
                onClick = { abiertoDialogoConfirmar = true },
                activado = formValidado,
                modifier = Modifier.weight(1f)
            )
            MiButton(
                accion = "Cancelar",
                onClick = onCancelarClick,
                modifier = Modifier.weight(1f)
            )
        } else {
            MiButton(
                accion = "Editar",
                onClick = onEditarClick,
                modifier = Modifier.weight(1f)
            )
            MiButton(
                accion = "Eliminar",
                onClick = { abiertoDialogoEliminar = true },
                modifier = Modifier.weight(1f)
            )
        }

        if(abiertoDialogoEliminar) {
            MiDialogoConfirmacion(
                icon = Icons.Filled.Delete,
                titulo = "Eliminar",
                texto = "¿Deseas eliminar a este usuario?",
                onConfirmar = { onEliminarClick() },
                onRechazarRequest = { abiertoDialogoEliminar = false },
            )
        }

        if(abiertoDialogoConfirmar) {
            MiDialogoConfirmacion(
                icon = Icons.Filled.Check,
                titulo = "Confirmar",
                texto = "¿Estás seguro que deseas modificar al usuario?",
                onConfirmar = { onConfirmarClick() },
                onRechazarRequest = { abiertoDialogoConfirmar = false },
            )
        }
    }
}