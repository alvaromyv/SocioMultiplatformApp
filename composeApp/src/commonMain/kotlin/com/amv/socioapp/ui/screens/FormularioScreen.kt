package com.amv.socioapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.amv.socioapp.ui.components.MiCheckBox
import com.amv.socioapp.ui.components.MiTextField
import com.amv.socioapp.ui.components.SeleccionarCategoria
import com.amv.socioapp.ui.viewmodel.InputViewModel
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher

@Composable
fun FormularioScreen(
    vm: InputViewModel = remember { InputViewModel() },
    modoEdicion: Boolean,
) {
    LaunchedEffect(modoEdicion) {
        if (modoEdicion) {
            return@LaunchedEffect
        } else {
            vm.reiniciarValores()
        }
    }

    FormularioContent(vm)
}
@Composable
private fun FormularioContent(
    vm: InputViewModel,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val launcher = rememberFilePickerLauncher(
            type = FileKitType.Image
        ) { file ->
            vm.actualizarAvatar(file.toString())
        }

        AsyncImage(
            model = vm.usuarioFormState.avatar,
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
                .clickable { launcher.launch() }
        )

        MiTextField(
            label = "Nombre",
            valor = vm.usuarioFormState.nombre,
            hayError = false,
            mensajeError = "",
            onValorChange = { vm.actualizarNombre(it) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = Icons.Filled.Person
        )

        MiTextField(
            label = "Apellidos",
            valor = vm.usuarioFormState.apellidos!!,
            hayError = false,
            mensajeError = "",
            onValorChange = { vm.actualizarApellidos(it) },
            modifier = Modifier.fillMaxWidth(),
        )

        MiTextField(
            label = "Telefono",
            valor = vm.usuarioFormState.telefono!!,
            hayError = vm.esTelefonoErroneo,
            mensajeError = "",
            onValorChange = { vm.actualizarTelefono(it) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = Icons.Filled.Phone,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        )

        MiTextField(
            label = "Email",
            valor = vm.usuarioFormState.email,
            hayError = vm.esEmailErroneo,
            mensajeError = "",
            onValorChange = { vm.actualizarEmail(it) },
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

        // ROL

        SeleccionarCategoria(
            categoria = vm.socioFormState.categoria,
            onCategoriaChange = { vm.actualizarCategoria(it) },
            modifier = Modifier.fillMaxWidth()
        )

        MiCheckBox(
            valor = vm.socioFormState.abonado,
            label = "Abonado",
            onValorChange = { vm.actualizarAbonado(it) },
        )

        // USUARIO ID
    }
}