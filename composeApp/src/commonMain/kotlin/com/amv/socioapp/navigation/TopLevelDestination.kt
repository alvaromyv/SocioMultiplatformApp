package com.amv.socioapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.amv.socioapp.model.Usuario
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
    ADMIN(Icons.Filled.Group, Icons.Outlined.Group, "Administración", getSerialName<Admin>()),
    PERFIL(Icons.Filled.Person, Icons.Outlined.Person, "Mi Perfil", getSerialName<Perfil>()),
    FORMULARIO(Icons.Filled.Edit, Icons.Outlined.Edit, "Formulario", getSerialName<Formulario>(), visibleNavigation = false)
}

@Serializable @SerialName("inicio")
data object Inicio

@Serializable @SerialName("administracion")
object Admin

@Serializable @SerialName("perfil")
object Perfil

@Serializable @SerialName("busqueda")
object Busqueda

@Serializable @SerialName("formulario")
data class Formulario(val modoEdicion: Boolean)

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> getSerialName(): String {
    return serializer<T>().descriptor.serialName
}

