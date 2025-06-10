package com.amv.socioapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.amv.socioapp.ui.components.MiTextFieldFecha
import com.amv.socioapp.ui.components.SeleccionButtonRow
import com.amv.socioapp.ui.viewmodel.InputViewModel
import com.amv.socioapp.util.PerfilAvatar
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.nameWithoutExtension
import io.github.vinceglb.filekit.readBytes
import io.ktor.util.PlatformUtils
import org.jetbrains.compose.ui.tooling.preview.Preview

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
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
    ) {
        val launcher = rememberFilePickerLauncher(
            type = FileKitType.Image
        ) { file -> vm.actualizarAvatar(file) }

        AsyncImage(
            model = vm.usuarioFormState.avatar,
            contentDescription = null,
            modifier = Modifier
                .wrapContentSize()
                .clickable { launcher.launch() }
        )

        // Text(vm.usuarioFormState.avatar.toString())

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