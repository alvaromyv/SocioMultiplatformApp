package com.amv.socioapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.amv.socioapp.data.SessionManager
import com.amv.socioapp.network.model.SocioBodyRequest
import com.amv.socioapp.ui.components.DetailPaneContent
import com.amv.socioapp.ui.screens.AdminScreen
import com.amv.socioapp.ui.screens.AgregaScreen
import com.amv.socioapp.ui.screens.BusquedaScreen
import com.amv.socioapp.ui.screens.PerfilScreen
import com.amv.socioapp.ui.viewmodel.AuthViewModel
import com.amv.socioapp.ui.viewmodel.SociosViewModel
import com.amv.socioapp.ui.viewmodel.UsuariosViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    sociosViewModel: SociosViewModel,
    usuariosViewModel: UsuariosViewModel
) {
    NavHost(
        navController = navController,
        startDestination = MiPerfil
    ) {
        composable<Admin> {
            AdminScreen(
                usuariosUiState = usuariosViewModel.usuariosUiState,
                onReintentarClick = { usuariosViewModel.leerTodos() },
                onEliminarClick = { id -> usuariosViewModel.borraUno(id) },
                onEditar = { usuarioRequest, socioRequest ->
                    if (usuarioRequest != null) {
                        usuariosViewModel.modificaUno(
                            usuarioRequest.id!!,
                            usuarioRequest.usuario,
                            usuarioRequest.avatar
                        )
                    }
                    if (socioRequest != null) {
                        if (socioRequest.id == null) sociosViewModel.creaUno(socioRequest.socio)
                        else sociosViewModel.modificaUno(socioRequest.id, socioRequest.socio)
                    }
                }
            )
        }

        composable<MiPerfil> {
            PerfilScreen(
                authUiState = authViewModel.authUiState,
                onEliminarClick = { },
                onEditarClick = { _, _ -> },
                onReintentarClick = { SessionManager.cerrarSesion() }
            )
        }

        composable<Busqueda> {
            BusquedaScreen(
                modifier = Modifier.fillMaxSize(),
                searchQuery = usuariosViewModel.searchQuery,
                searchResults = usuariosViewModel.buscados,
                onSearchQueryChanged = { usuariosViewModel.searchQuery = it },
                onSearchTriggered = { query -> usuariosViewModel.buscarUsuario(query) },
                onItemClick = { id ->
                    navController.navigate(Perfil(id))
                },
                onBackClick = { navController.popBackStack() },
            )
        }

        composable<Agrega> { backStackEntry ->
            AgregaScreen(
                onCrearClick = { usuarioRequest, socioRequest ->
                    authViewModel.registrarUsuario(
                        usuarioRequest.usuario,
                        usuarioRequest.avatar,
                        onSuccess = { id ->
                            if (socioRequest != null) {
                                sociosViewModel.creaUno(
                                    SocioBodyRequest(
                                        categoria = socioRequest.socio.categoria,
                                        fechaAntiguedad = socioRequest.socio.fechaAntiguedad,
                                        abonado = socioRequest.socio.abonado,
                                        usuarioId = id
                                    ),
                                    { usuariosViewModel.leerTodos() }
                                )
                                usuariosViewModel.leerTodos()
                            }
                        }
                    )
                    navController.popBackStack()
                }
            )
        }

        composable<Perfil> { navBackStackEntry ->
            val usuarioId = navBackStackEntry.toRoute<Perfil>().usuarioId
            var edicion by remember { mutableStateOf(false) }
            LaunchedEffect(usuarioId) { usuariosViewModel.leeUno(usuarioId) }

            if (usuariosViewModel.seleccionado != null) {
                DetailPaneContent(
                    usuario = usuariosViewModel.seleccionado!!,
                    onEliminarClick = { id -> usuariosViewModel.borraUno(id) },
                    onEditarClick = { usuarioRequest, socioRequest ->
                        if (usuarioRequest != null) {
                            usuariosViewModel.modificaUno(
                                usuarioRequest.id!!,
                                usuarioRequest.usuario,
                                usuarioRequest.avatar
                            )
                        }
                        if (socioRequest != null) {
                            if (socioRequest.id == null) sociosViewModel.creaUno(socioRequest.socio)
                            else sociosViewModel.modificaUno(socioRequest.id, socioRequest.socio)
                        }
                    },
                    edicion = edicion,
                    onEdicionChange = { edicion = it },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}