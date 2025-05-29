package com.amv.socioapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Stadium
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Stadium
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
) {
    INICIO(Icons.Filled.Home, Icons.Outlined.Home, "Inicio", getSerialName<Inicio>()),
    SOCIOS(Icons.Filled.Person, Icons.Outlined.Person, "Socios", getSerialName<Socios>()),
    CLUB(Icons.Filled.Stadium, Icons.Outlined.Stadium, "Club", getSerialName<Club>())
}

@Serializable
@SerialName("inicio")
data object Inicio

@Serializable
@SerialName("socios")
object Socios

@Serializable
@SerialName("club")
object Club


@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> getSerialName(): String {
    return serializer<T>().descriptor.serialName
}

