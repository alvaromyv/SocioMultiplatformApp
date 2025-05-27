package com.amv.socioapp.data

import com.amv.socioapp.data.datasource.firestore.FirestoreSocioDataSource
import com.amv.socioapp.model.Socio
import kotlinx.coroutines.flow.Flow

interface SociosRepository {
    suspend fun obtenerSocios(): Flow<List<Socio>>

}
class SociosRepositoryImpl(
    private val firestoreSocioDataSource: FirestoreSocioDataSource
) : SociosRepository {
    override suspend fun obtenerSocios(): Flow<List<Socio>> = firestoreSocioDataSource.leerTodos()
}