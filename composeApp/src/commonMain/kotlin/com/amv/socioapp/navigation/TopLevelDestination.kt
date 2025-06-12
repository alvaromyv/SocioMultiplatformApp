package com.amv.socioapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
//    @StringRes val iconTextId: Int,
//    @StringRes val titleTextId: Int,
    val label: String,
    val route: String,
    val visibleNavigation: Boolean = true
) {
    INICIO(Icons.Filled.Home, Icons.Outlined.Home, "Inicio", getSerialName<Inicio>()),
    SOCIOS(Icons.Filled.Group, Icons.Outlined.Group, "Socios", getSerialName<Socios>()),
    USUARIOS(Icons.Filled.Person, Icons.Outlined.Person, "Usuarios", getSerialName<Usuarios>()),

    ADMIN(Icons.Filled.Person, Icons.Outlined.Person, "Administración", getSerialName<Admin>()),

    FORMULARIO(Icons.Filled.Edit, Icons.Outlined.Edit, "Formulario", getSerialName<Formulario>(), visibleNavigation = false)
}

@Serializable @SerialName("inicio")
data object Inicio

@Serializable @SerialName("socios")
object Socios

@Serializable @SerialName("usuarios")
object Usuarios

@Serializable @SerialName("administracion")
object Admin

@Serializable @SerialName("formulario")
data class Formulario(val modoEdicion: Boolean)

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> getSerialName(): String {
    return serializer<T>().descriptor.serialName
}

