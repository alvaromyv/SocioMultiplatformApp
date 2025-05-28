package com.amv.socioapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.amv.socioapp.model.Socio
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun SociosScreen(
    socios: List<Socio>
) {
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<Socio>()
    val scope = rememberCoroutineScope()

    /*NavigableListDetailPaneScaffold(
        navigator = scaffoldNavigator,
        listPane = {
            AnimatedPane {
                ListaSocios(
                    socio = socios,
                    onClick = { cliente ->
                        // Navigate to the detail pane with the passed client
                        scope.launch {
                            scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail, cliente)
                        }
                    },
                )
            }
        },
        detailPane = {
            AnimatedPane {
                scaffoldNavigator.currentDestination?.contentKey?.let { socio ->
                    DetalleSocio(socio)
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    )*/
}

@Composable
fun ListaSocios(socio: Socio, onClick: (Socio) -> Unit) {

}

@Composable
fun DetalleSocio(socio: Socio) {

}