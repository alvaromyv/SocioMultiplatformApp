package com.amv.socioapp.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.amv.socioapp.ui.screens.SociosScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Inicio
    ) {
        composable<Inicio> {
            Text("INICIO")
        }
        composable<Socios> {
            SociosScreen()
        }
        composable<Club> {
            Text("CLUB")
        }
    }
}