package com.amv.socioapp.data

import com.amv.socioapp.data.datasource.firestore.SociosFirestoreDataSource
import com.amv.socioapp.model.Socio

interface SociosRepository {
    suspend fun obtenerSocios(): List<Socio>

class SociosRepositoryImpl(
    private val sociosFirestoreDataSource: SociosFirestoreDataSource
) : SociosRepository {
    override suspend fun obtenerSocios(): List<Socio> = TODO()
}