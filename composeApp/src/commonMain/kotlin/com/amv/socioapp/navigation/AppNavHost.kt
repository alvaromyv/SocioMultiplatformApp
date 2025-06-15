package com.amv.socioapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.amv.socioapp.data.SessionManager
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
        startDestination = Admin
    ) {
        composable<Admin>{
            AdminScreen(
                usuariosUiState = usuariosViewModel.usuariosUiState,
                onReintentarClick = { usuariosViewModel.leerTodos() },
                onEliminarClick = { id -> usuariosViewModel.borraUno(id) },
                onEditar = { usuarioRequest, socioRequest ->
                    if (usuarioRequest != null) { usuariosViewModel.modificaUno(usuarioRequest.id!!, usuarioRequest.usuario, usuarioRequest.avatar)}
                    if (socioRequest != null) {
                        if(socioRequest.id == null) sociosViewModel.creaUno(socioRequest.socio)
                        else sociosViewModel.modificaUno(socioRequest.id, socioRequest.socio)
                    }
                }
            )
        }

        composable<Perfil> {
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
                onBackClick = { navController.popBackStack() },
            )
        }

        composable<Agrega> { backStackEntry ->
            AgregaScreen()
        }

    }
}