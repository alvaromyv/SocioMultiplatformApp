package com.amv.socioapp

import com.amv.socioapp.data.AppContainer
import com.amv.socioapp.data.DefaultAppContainer

object AppEnvironment {
    private lateinit var contenedor: AppContainer
    fun setupAppEnvironment() {
        contenedor = DefaultAppContainer
    }
}