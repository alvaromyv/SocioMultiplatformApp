package com.amv.socioapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import coil3.compose.AsyncImage
import com.amv.socioapp.model.Socio
import com.amv.socioapp.model.Usuario
import com.amv.socioapp.ui.components.MiCheckBox
import com.amv.socioapp.ui.components.MiLabel
import com.amv.socioapp.ui.components.MiTextField
import com.amv.socioapp.ui.components.MiTextFieldFecha
import com.amv.socioapp.ui.components.SeleccionButtonRow
import com.amv.socioapp.ui.viewmodel.InputViewModel
import com.amv.socioapp.util.PerfilAvatar
import com.amv.socioapp.util.responsiveLayout
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FormularioScreen(
    vm: InputViewModel = remember { InputViewModel() },
    seleccionado: Usuario?,
    modoEdicion: Boolean,
    onDispose: () -> Unit,
) {
    LaunchedEffect(modoEdicion, seleccionado) {
        if (modoEdicion && seleccionado != null) {
             vm.cargarUsuario(seleccionado)
        } else {
            vm.reiniciarValores()
        }
    }

    DisposableEffect(Unit) {
        onDispose { onDispose() }
    }

    FormularioContent(vm, modoEdicion)
}

@Composable
private fun FormularioContent(
    vm: InputViewModel,
    modoEdicion: Boolean,
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .responsiveLayout()
                .verticalScroll(scrollState)
        ) {
            MiLabel("USUARIO", style = MaterialTheme.typography.headlineMedium)
            val launcher = rememberFilePickerLauncher(
                type = FileKitType.Image
            ) { file -> vm.actualizarAvatar(file) }

            AsyncImage(
                model = vm.usuarioFormState.avatar,
                contentDescription = vm.usuarioFormState.avatar.toString(),
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { launcher.launch() }
            )

            MiTextField(
                label = "Nombre",
                valor = vm.usuarioFormState.nombre,
                hayError = false,
                mensajeError = "",
                onValorChange = vm::actualizarNombre,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = Icons.Filled.Person
            )

            MiTextField(
                label = "Apellidos",
                valor = vm.usuarioFormState.apellidos!!,
                hayError = false,
                mensajeError = "",
                onValorChange = vm::actualizarApellidos,
                modifier = Modifier.fillMaxWidth(),
            )

            MiTextField(
                label = "Telefono",
                valor = vm.usuarioFormState.telefono!!,
                hayError = vm.esTelefonoErroneo,
                mensajeError = "",
                onValorChange = vm::actualizarTelefono,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = Icons.Filled.Phone,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            )

            MiTextField(
                label = "Email",
                valor = vm.usuarioFormState.email,
                hayError = vm.esEmailErroneo,
                mensajeError = "",
                onValorChange = vm::actualizarEmail,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = Icons.Filled.Email,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )

            MiTextField(
                label = "Contraseña",
                valor = vm.usuarioFormState.password,
                hayError = false,
                mensajeError = "",
                onValorChange = vm::actualizarPassword,
                modifier = Modifier.fillMaxWidth(),
                esVisible = vm.esPasswordVisible,
                trailingIcon = Icons.Filled.Visibility,
                onTrailingIconClick = vm::actualizarPasswordVisible,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )

            SeleccionButtonRow(
                valor = vm.usuarioFormState.rol,
                onValorChange = vm::actualizarRol,
                modifier = Modifier.fillMaxWidth()
            )

            MiLabel("SOCIO", style = MaterialTheme.typography.headlineMedium)

            SeleccionButtonRow(
                valor = vm.socioFormState.categoria,
                onValorChange = vm::actualizarCategoria,
                modifier = Modifier.fillMaxWidth()
            )

            MiTextFieldFecha(
                label = "Fecha de nacimiento",
                fecha = vm.socioFormState.fechaNacimiento,
                hayError = false,
                onFechaChange = vm::actualizarFechaNacimiento,
                modifier = Modifier.fillMaxWidth()
            )

            MiTextFieldFecha(
                label = "Fecha de antiguedad",
                fecha = vm.socioFormState.fechaAntiguedad,
                hayError = false,
                onFechaChange = vm::actualizarFechaAntiguedad,
                modifier = Modifier.fillMaxWidth()
            )

            MiCheckBox(
                valor = vm.socioFormState.abonado,
                label = "Abonado",
                onValorChange = vm::actualizarAbonado,
                modifier = Modifier.fillMaxWidth()
            )

            // USUARIO ID
        }
    }
}