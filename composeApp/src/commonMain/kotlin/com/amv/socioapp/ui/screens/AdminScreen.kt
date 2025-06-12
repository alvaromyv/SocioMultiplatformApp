package com.amv.socioapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.TabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.amv.socioapp.model.Socio
import com.amv.socioapp.model.Usuario
import com.amv.socioapp.navigation.Formulario
import com.amv.socioapp.ui.components.ExceptionScreen
import com.amv.socioapp.ui.components.LoadingScreen
import com.amv.socioapp.ui.viewmodel.SociosUiState
import com.amv.socioapp.ui.viewmodel.UsuariosUiState
import com.amv.socioapp.util.PerfilAvatar
import kotlinx.coroutines.launch

@Composable
fun AdminScreen(
    usuariosUiState: UsuariosUiState,
    onReintentarClick: () -> Unit,
) {
    when(val estado = usuariosUiState) {
        is UsuariosUiState.Success -> AdminListDetailPaneScaffold(estado.usuarios)
        UsuariosUiState.Loading -> LoadingScreen()
        is UsuariosUiState.Error -> {}
        is UsuariosUiState.Exception -> ExceptionScreen { onReintentarClick }
    }
}


@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)
@Composable
private fun AdminListDetailPaneScaffold(
    usuarios: List<Usuario>,
) {
    val scope = rememberCoroutineScope()
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<Usuario>()
    val selectedItem = scaffoldNavigator.currentDestination?.contentKey
    var indiceSeleccionado by remember { mutableIntStateOf(0) }

    ListDetailPaneScaffold(
        directive = scaffoldNavigator.scaffoldDirective,
        scaffoldState = scaffoldNavigator.scaffoldState,
        listPane = {
            TabOpciones(indiceSeleccionado, onTabSeleccionado = { indice -> indiceSeleccionado = indice})
            AnimatedPane {
                ListPaneContent(
                    items = usuarios,
                    onItemClick = { item ->
                        scope.launch {
                            scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail, item)
                        }
                    },
                    indiceSeleccionado = indiceSeleccionado,
                )
            }
        },
        detailPane = {
            AnimatedPane {
                selectedItem?.let { item ->

                }
            }
        }
    )

    BackHandler(enabled = scaffoldNavigator.canNavigateBack()) {
        scope.launch {
            scaffoldNavigator.navigateBack()
        }
    }

}

@Composable
private fun ListPaneContent(
    items: List<Usuario>,
    onItemClick: (Usuario) -> Unit,
    indiceSeleccionado: Int
){
    val filtrados: List<Usuario> = emptyList()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        items(filtrados) { item ->
            ListItem(
                headlineContent = { Text(item.obtenerNombreCompleto()) },
                supportingContent = { },
                leadingContent = {
                    PerfilAvatar(
                        avatarLink = item.avatarUrl,
                        contentDescription = item.obtenerNombreCompleto()
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 12.dp)
                    .clickable { onItemClick(item) }
            )
        }
    }

    when(indiceSeleccionado) {
        0 -> {}
        1 -> {}
    }
}



@Composable
private fun DetailPaneContent(

){

}

@Composable
private fun TabOpciones(
    indiceSeleccionado: Int,
    onTabSeleccionado: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val opciones = listOf("Socios", "Usuarios")

    TabRow(selectedTabIndex = indiceSeleccionado, modifier = modifier.fillMaxWidth()) {
        opciones.forEachIndexed { indice, opcion ->
            Tab(
                selected = indiceSeleccionado == indice,
                onClick = { onTabSeleccionado(indice) },
                text = { Text(opcion) },
            )
        }
    }
}