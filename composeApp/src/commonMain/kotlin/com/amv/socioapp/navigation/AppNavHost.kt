package com.amv.socioapp.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.amv.socioapp.ui.screens.FormularioScreen
import com.amv.socioapp.ui.screens.SociosScreen
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
        composable<Socios> {
            SociosScreen(navController, sociosViewModel)
        }
        composable<Usuarios> {
            Text("USUARIOS")
        }
        composable<Formulario> { backStackEntry ->
            val modoEdicion = backStackEntry.toRoute<Formulario>().modoEdicion
            FormularioScreen(modoEdicion = modoEdicion)
        }
    }
}