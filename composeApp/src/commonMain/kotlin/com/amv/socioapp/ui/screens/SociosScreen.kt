package com.amv.socioapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amv.socioapp.model.Categoria
import com.amv.socioapp.model.Socio
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun SociosScreen(
    socios: List<Socio> = listOf(
        Socio(
            dni = "12345678A",
            nSocio = 1,
            nombre = "Juan",
            apellidos = "Pérez",
            categoria = Categoria.SENIOR,
            esAbonado = true,
            direccion = "Calle Falsa 123",
            notas = "Siempre puntual"
        ),
        Socio(
            dni = "87654321B",
            nSocio = 2,
            nombre = "Ana",
            apellidos = "López",
            categoria = Categoria.INFANTIL,
            esAbonado = false,
            direccion = "Avenida Siempre Viva 742",
            notas = null
        ),
        Socio(
            dni = "11223344C",
            nSocio = 3,
            nombre = "Carlos",
            apellidos = "Martínez",
            categoria = Categoria.SENIOR,
            esAbonado = true,
            direccion = null,
            notas = "Socio fundador"
        )
    )
) {
    val scope = rememberCoroutineScope()
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<Socio?>()
    val selectedItem = scaffoldNavigator.currentDestination?.contentKey

    ListDetailPaneScaffold(
        directive = scaffoldNavigator.scaffoldDirective,
        scaffoldState = scaffoldNavigator.scaffoldState,
        listPane = {
            AnimatedPane {
                ListPaneContent(
                    items = socios,
                    onItemClick = { item ->
                        scope.launch {
                            scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail, item)
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                selectedItem?.let {
                    scaffoldNavigator.currentDestination?.contentKey?.let { item ->
                        DetailPaneContent(item)
                    }
                }
            }
        }
    )

}

@Composable
fun ListPaneContent(
    items: List<Socio>,
    onItemClick: (Socio) -> Unit,
) {
    LazyColumn(
        
    ) { 
        items(items) { item ->
            ListItem(
                headlineContent = { Text(item.obtenerNombreCompleto()) },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = "Avatar",
                        modifier = Modifier.size(40.dp)
                    )
                },
                modifier = Modifier.clickable { onItemClick(item) }
            )
        }
    }
}

@Composable
fun DetailPaneContent(
    socio: Socio,
) {
    Column() {
        Text(text = "DNI: ${socio.dni}")
        Text(text = "Nº Socio: ${socio.nSocio}")
        Text(text = "Nombre: ${socio.nombre}")
        Text(text = "Apellidos: ${socio.apellidos}")
        Text(text = "Categoría: ${socio.categoria}")
        Text(text = "Abonado: ${if (socio.esAbonado) "Sí" else "No"}")
        Text(text = "Dirección: ${socio.direccion ?: "Sin especificar"}")
        Text(text = "Notas: ${socio.notas ?: "Sin notas"}")
    }
}
