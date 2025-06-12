package com.amv.socioapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
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
import androidx.window.core.layout.WindowWidthSizeClass
import com.amv.socioapp.model.Socio
import com.amv.socioapp.navigation.Formulario
import com.amv.socioapp.ui.components.ExceptionScreen
import com.amv.socioapp.ui.components.LoadingScreen
import com.amv.socioapp.ui.components.MiButton
import com.amv.socioapp.ui.components.MiDialogoConfirmacion
import com.amv.socioapp.ui.viewmodel.SociosUiState
import com.amv.socioapp.ui.viewmodel.SociosViewModel
import com.amv.socioapp.util.PerfilAvatar
import kotlinx.coroutines.launch


@Composable
fun SociosScreen(
    navController: NavHostController,
    vm: SociosViewModel,
) {
    when(val estado = vm.sociosUiState) {
        is SociosUiState.Success -> ListDetailPaneScaffoldSocios(
            socios = estado.socios,
            onEditarClick = { id ->
                vm.leeUno(id) // Leemos para comprobar que sigue existiendo
                navController.navigate(Formulario(true))
            },
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
                headlineContent = { Text(item.usuario?.obtenerNombreCompleto() ?: "") },
                supportingContent = {
                    Row (modifier = Modifier.fillMaxWidth()) {
                        Text(text = item.categoria.name, modifier = Modifier.weight(1f))
                        Text(text = Socio.formatearFecha(item.fechaAntiguedad), modifier = Modifier.weight(1f), textAlign = TextAlign.End)
                    }
                },
                leadingContent = {
                    PerfilAvatar(
                        avatarLink = item.usuario?.avatarUrl ?: "",
                        contentDescription = item.usuario?.obtenerNombreCompleto()
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
fun DetailPaneContent(
    socio: Socio,
    onEliminarClick: (Int) -> Unit,
    onEditarClick: (Int) -> Unit
) {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val windowSizeClass = windowAdaptiveInfo.windowSizeClass
    val isExpanded = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED

    OutlinedCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    if (isExpanded) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 24.dp)
                        ) {
                            PerfilAvatar(
                                avatarLink = socio.usuario?.avatarUrl ?: "",
                                contentDescription = socio.usuario?.obtenerNombreCompleto(),
                                iconSize = 128.dp
                            )

                            Spacer(modifier = Modifier.width(24.dp))

                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(
                                    text = socio.usuario?.obtenerNombreCompleto() ?: "",
                                    style = MaterialTheme.typography.headlineLarge
                                )
                                Text(
                                    text = "Nº${socio.nSocio}",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 24.dp)
                        ) {
                            PerfilAvatar(
                                avatarLink = socio.usuario?.avatarUrl ?: "",
                                contentDescription = socio.usuario?.obtenerNombreCompleto(),
                                iconSize = 112.dp
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = socio.usuario?.obtenerNombreCompleto() ?: "",
                                style = MaterialTheme.typography.headlineMedium,
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = "Nº${socio.nSocio}",
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "Contacto:",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(text = socio.usuario?.email ?: "", style = MaterialTheme.typography.bodyLarge)
                        Text(text = socio.usuario?.telefono ?: "Desconocido", style = MaterialTheme.typography.bodyLarge)

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Información adicional:",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = "Fecha de nacimiento: ${Socio.formatearFecha(socio.fechaNacimiento)}",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            text = "Fecha de antigüedad: ${Socio.formatearFecha(socio.fechaAntiguedad)}",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            text = "Categoría: ${socio.categoria.name}",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            text = "Cuota mensual: ${socio.categoria.cuota} €",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OpcionesUsuario(
                    onEliminarClick = { onEliminarClick(socio.id) },
                    onEditarClick = { onEditarClick(socio.id) }
                )
            }
        }
    }
}



@Composable
private fun OpcionesUsuario(
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