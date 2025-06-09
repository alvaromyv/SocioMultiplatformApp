package com.amv.socioapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.amv.socioapp.model.Socio
import com.amv.socioapp.navigation.Formulario
import com.amv.socioapp.ui.components.ExceptionScreen
import com.amv.socioapp.ui.components.LoadingScreen
import com.amv.socioapp.ui.components.MiButton
import com.amv.socioapp.ui.components.MiDialogoConfirmacion
import com.amv.socioapp.ui.viewmodel.SociosUiState
import com.amv.socioapp.ui.viewmodel.SociosViewModel
import com.amv.socioapp.util.UsuarioAvatar
import kotlinx.coroutines.launch


@Composable
fun SociosScreen(
    navController: NavHostController,
    vm: SociosViewModel,
) {
    LaunchedEffect(Unit) { vm.leerTodos() }
    when(val estado = vm.sociosUiState) {
        is SociosUiState.Success -> ListDetailPaneScaffoldSocios(
            socios = estado.socios,
            onEditarClick = { navController.navigate(Formulario(true)) },
            onEliminarClick = { id -> vm.borraUno(id) }
        )
        SociosUiState.Loading -> LoadingScreen()
        is SociosUiState.Error -> {}
        is SociosUiState.Exception -> ExceptionScreen { vm.leerTodos() }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)
@Composable
private fun ListDetailPaneScaffoldSocios(
    socios: List<Socio>,
    onEditarClick: (Int) -> Unit,
    onEliminarClick: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<Socio>()
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
                selectedItem?.let { item ->
                    DetailPaneContent(item, onEliminarClick, onEditarClick)
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
    items: List<Socio>,
    onItemClick: (Socio) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(items) { item ->
            ListItem(
                headlineContent = { Text(item.usuario.obtenerNombreCompleto()) },
                supportingContent = {
                    Row (modifier = Modifier.fillMaxWidth()) {
                        Text(text = item.categoria.name, modifier = Modifier.weight(1f))
                        Text(text = Socio.formatearFecha(item.fechaAntiguedad), modifier = Modifier.weight(1f), textAlign = TextAlign.End)
                    }
                },
                leadingContent = {
                    UsuarioAvatar(
                        imageUrl = item.usuario.avatarUrl,
                        contentDescription = item.usuario.obtenerNombreCompleto()
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

@Composable
private fun DetailPaneContent(
    socio: Socio,
    onEliminarClick: (Int) -> Unit,
    onEditarClick: (Int) -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                UsuarioAvatar(
                    imageUrl = socio.usuario.avatarUrl,
                    contentDescription = socio.usuario.obtenerNombreCompleto(),
                    iconSize = 128.dp
                )

                Column(
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Text(text = socio.usuario.obtenerNombreCompleto(), style = MaterialTheme.typography.headlineMedium)
                    Text(text = "Socio Nº${socio.nSocio}", style = MaterialTheme.typography.headlineSmall)
                }
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outline)

            ////////////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////////////

            OpcionesSocio(
                onEliminarClick = { onEliminarClick(socio.id) },
                onEditarClick = { onEditarClick(socio.id) }
            )
        }
    }
}

@Composable
private fun OpcionesSocio(
    onEliminarClick: () -> Unit,
    onEditarClick: () -> Unit,
) {
    val abiertoDialogoEliminar = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        MiButton(
            accion = "Editar",
            onClick = onEditarClick ,
            modifier = Modifier.weight(1f)
        )
        MiButton(
            accion = "Eliminar",
            onClick = { abiertoDialogoEliminar.value = true },
            modifier = Modifier.weight(1f)
        )
    }

    if (abiertoDialogoEliminar.value) {
        MiDialogoConfirmacion(
            onRechazarRequest = { abiertoDialogoEliminar.value = false },
            onConfirmar = {
                abiertoDialogoEliminar.value = false
                onEliminarClick
            },
            titulo = "Eliminar",
            texto = "¿Estás seguro que quieres eliminar a este socio?",
            icon = Icons.Filled.Delete
        )
    }
}