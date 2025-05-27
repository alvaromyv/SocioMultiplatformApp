package com.amv.socioapp.data.datasource.firestore

import com.amv.socioapp.model.Socio
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow

class FirestoreSocioDataSource(
    private val firestore: FirebaseFirestore = Firebase.firestore
) {
    fun leerTodos() = flow {
        firestore.collection("SOCIOS").snapshots.collect { querySnapshot ->
            val socios = querySnapshot.documents.map { document ->
                document.data<Socio>()
            }
            emit(socios)
        }
    }

}