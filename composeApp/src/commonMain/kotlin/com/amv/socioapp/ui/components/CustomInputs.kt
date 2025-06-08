package com.amv.socioapp.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun MiTextField(
    label: String,
    valor: String,
    hayError: Boolean,
    mensajeError: String,
    onValorChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    esVisible: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onLeadingIconClick: () -> Unit = { },
    onTrailingIconClick: () -> Unit = { },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    enabled: Boolean = true,
    maxLines: Int = 1,
) {
    OutlinedTextField(
        label = { Text(label) },
        value = valor,
        onValueChange = { valor -> onValorChange(valor) },
        isError = hayError,
        supportingText = {
            if (hayError) {
                Text(mensajeError)
            }
        },
        modifier = modifier,
        leadingIcon =
            if (leadingIcon != null) {
                {
                    IconButton(onClick = onLeadingIconClick) {
                        Icon(imageVector = leadingIcon, contentDescription = label)
                    }
                }
            } else null,
        trailingIcon =
            if (trailingIcon != null) {
                {
                    IconButton(onClick = onTrailingIconClick) {
                        Icon(
                            imageVector =
                                if (esVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = label
                        )
                    }
                }
            } else null,
        keyboardOptions = keyboardOptions,
        visualTransformation = if (esVisible) VisualTransformation.None else PasswordVisualTransformation(),
        enabled = enabled,
        maxLines = maxLines,
        shape = RoundedCornerShape(10.dp),
    )
}

@Composable
fun MiDoubleTextField(
    label: String,
    valor: Double,
    hayError: Boolean,
    mensajeError: String,
    onValorChange: (Double) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    onLeadingIconClick: () -> Unit = { },
) {
    OutlinedTextField(
        label = { Text(label) },
        value = valor.toString(),
        onValueChange = { valor: String -> onValorChange(valor.toDoubleOrNull() ?: 0.0) },
        leadingIcon = {
            if (leadingIcon != null) {
                IconButton(onClick = onLeadingIconClick) {
                    Icon(imageVector = leadingIcon, contentDescription = label)
                }
            } else null
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        maxLines = 1,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier,
        isError = hayError,
        supportingText = {
            if (hayError) {
                Text(mensajeError)
            }
        }
    )
}

@Composable
fun MiCheckBox(
    valor: Boolean,
    label: String,
    onValorChange: (Boolean) -> Unit,
    modifier: Modifier
) {
    Checkbox(
        checked = valor,
        onCheckedChange = { valor -> onValorChange(valor) },
        modifier = modifier
    )
    Text(
        text = label,
        modifier = modifier,
    )
}

@Composable
fun MiButton(
    accion: String,
    onClick: () -> Unit,
    activado: Boolean = true,
    modifier: Modifier
) {
    Button(
        onClick = onClick,
        enabled = activado,
        shape = RoundedCornerShape(32),
        modifier = modifier
    ) {
        Text(accion)
    }
}

