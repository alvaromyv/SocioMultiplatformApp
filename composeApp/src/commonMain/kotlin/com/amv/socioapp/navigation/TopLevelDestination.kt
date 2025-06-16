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
import org.jetbrains.compose.resources.StringResource
import sociomultiplatformapp.composeapp.generated.resources.Res
import sociomultiplatformapp.composeapp.generated.resources.administracion_titulo
import sociomultiplatformapp.composeapp.generated.resources.agrega_titulo
import sociomultiplatformapp.composeapp.generated.resources.mi_perfil_titulo

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: StringResource,
    val route: String,
    val visibleNavigation: Boolean = true
) {
    ADMIN(Icons.Filled.Group, Icons.Outlined.Group, Res.string.administracion_titulo, getSerialName<Admin>()),
    MI_PERFIL(Icons.Filled.Person, Icons.Outlined.Person, Res.string.mi_perfil_titulo, getSerialName<MiPerfil>()),
    PERFIL(Icons.Filled.Person, Icons.Outlined.Person, Res.string.mi_perfil_titulo, getSerialName<Perfil>(), visibleNavigation = false),
    AGREGAR(Icons.Filled.Edit, Icons.Outlined.Edit, Res.string.agrega_titulo, getSerialName<Agrega>(), visibleNavigation = false)
}

@Serializable @SerialName("administracion")
object Admin

@Serializable @SerialName("perfil")
object MiPerfil

@Serializable @SerialName("perfil")
data class Perfil(val usuarioId: Int)

@Serializable @SerialName("busqueda")
object Busqueda

@Serializable @SerialName("agrega")
object Agrega

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> getSerialName(): String {
    return serializer<T>().descriptor.serialName
}

