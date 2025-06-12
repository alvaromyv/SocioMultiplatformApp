package com.amv.socioapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import com.amv.socioapp.model.Usuario
import com.amv.socioapp.ui.components.ExceptionScreen
import com.amv.socioapp.ui.components.LoadingScreen
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

    ListDetailPaneScaffold(
        directive = scaffoldNavigator.scaffoldDirective,
        scaffoldState = scaffoldNavigator.scaffoldState,
        listPane = {
            AnimatedPane {
                ListPaneContent(
                    items = usuarios,
                    onItemClick = { item ->
                        scope.launch {
                            scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail, item)
                        }
                    },
                )
            }
        },
        detailPane = {
            AnimatedPane {
                selectedItem?.let { item ->
                    DetailPaneContent(
                        usuario = item,
                        onEliminarClick = { /*vm.borraUno(it)*/ },
                        onEditarClick = { /*vm.leeUno(it)*/ }
                    )
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
){
    var indiceSeleccionado by remember { mutableIntStateOf(0) }
    val filtrados: List<Usuario> = when(indiceSeleccionado) {
        1 -> items.filter { it.socio != null }
        2 -> items.filter { it.socio == null }
        else -> items
    }

    Column {
        TabOpciones(indiceSeleccionado, onTabSeleccionado = { indice -> indiceSeleccionado = indice} )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            items(filtrados) { item ->
                ListItem(
                    headlineContent = { Text(item.obtenerNombreCompleto()) },
                    supportingContent = {
                        if (item.socio != null) {
                            Row (modifier = Modifier.fillMaxWidth()) {
                                Text(text = item.socio.obtenerNumeracionFormateada(), modifier = Modifier.weight(1f))
                                Text(text = item.socio.categoria.name, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
                            }
                        } else {
                            Text(text = item.rol.name)
                        }
                    },
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
    }
}



@Composable
private fun DetailPaneContent(
    usuario: Usuario,
    onEliminarClick: (Int) -> Unit,
    onEditarClick: (Int) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {

    }
}

@Composable
private fun TabOpciones(
    indiceSeleccionado: Int,
    onTabSeleccionado: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val opciones = listOf("Todos", "Socios", "Usuarios")

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