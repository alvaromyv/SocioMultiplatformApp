package com.amv.socioapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.amv.socioapp.ui.screens.AdminScreen
import com.amv.socioapp.ui.screens.BusquedaScreen
import com.amv.socioapp.ui.screens.FormularioScreen
import com.amv.socioapp.ui.screens.PerfilScreen
import com.amv.socioapp.ui.viewmodel.SociosViewModel
import com.amv.socioapp.ui.viewmodel.UsuariosViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    sociosViewModel: SociosViewModel,
    usuariosViewModel: UsuariosViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Inicio
    ) {
        composable<Inicio> {
            Text("INICIO")
        }

        composable<Admin>{
            AdminScreen(
                usuariosUiState = usuariosViewModel.usuariosUiState,
                onReintentarClick = { usuariosViewModel.leerTodos() }
            )
        }

        composable<Perfil> {
            PerfilScreen()
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

        composable<Formulario> { backStackEntry ->
            val modoEdicion = backStackEntry.toRoute<Formulario>().modoEdicion
            FormularioScreen(
                modoEdicion = modoEdicion,
                seleccionado = usuariosViewModel.seleccionado,
                onDispose = { usuariosViewModel.seleccionado = null },
            )
        }

    }
}