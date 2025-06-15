package com.amv.socioapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Group
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
    ADMIN(Icons.Filled.Group, Icons.Outlined.Group, "Administración", getSerialName<Admin>()),
    PERFIL(Icons.Filled.Person, Icons.Outlined.Person, "Mi Perfil", getSerialName<Perfil>()),
    AGREGAR(Icons.Filled.Edit, Icons.Outlined.Edit, "Agregar", getSerialName<Agrega>(), visibleNavigation = false)
}

@Serializable @SerialName("administracion")
object Admin

@Serializable @SerialName("perfil")
object Perfil

@Serializable @SerialName("busqueda")
object Busqueda

@Serializable @SerialName("agrega")
object Agrega

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> getSerialName(): String {
    return serializer<T>().descriptor.serialName
}

