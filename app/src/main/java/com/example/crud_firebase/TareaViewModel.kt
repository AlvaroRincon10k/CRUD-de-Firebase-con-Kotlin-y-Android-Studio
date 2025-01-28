package com.example.crud_firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TareaViewModel: ViewModel() {

    private val db = Firebase.firestore

    private var _listaTareas = MutableLiveData<List<Tarea>>(emptyList())
    val listaTarea: LiveData<List<Tarea>> = _listaTareas

    init {
        obtenerTareas()
    }

    private fun obtenerTareas() {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val resultado = db.collection("tareas").get().await()

                val tareas = resultado.documents.mapNotNull { it.toObject(Tarea::class.java) }
                _listaTareas.postValue(tareas)
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}