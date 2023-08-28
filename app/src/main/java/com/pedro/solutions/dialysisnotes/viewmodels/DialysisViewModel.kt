package com.pedro.solutions.dialysisnotes.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.pedro.solutions.dialysisnotes.DialysisApplication
import com.pedro.solutions.dialysisnotes.model.Dialysis
import com.pedro.solutions.dialysisnotes.model.DialysisDAO
import com.pedro.solutions.dialysisnotes.views.AddEditDialysisEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DialysisViewModel(
    private val dao: DialysisDAO, private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val dialysisList: LiveData<List<Dialysis>> = dao.getAllDialysis().asLiveData()
    private val dialysisId: Int? = savedStateHandle["userId"]



    var dialysis by mutableStateOf<Dialysis?>(null)
        private set

    var initialUF by mutableIntStateOf(-1)
        private set
    var finalUF by mutableIntStateOf(-1)
        private set

    var observation by mutableStateOf("")
        private set

    fun setCurrentDialysis(id: Int) {
        if (id != -1) {
            viewModelScope.launch {
                dao.findDialysisById(id)?.let { dialysis ->
                    initialUF = dialysis.initialUf
                    finalUF = dialysis.finalUf
                    observation = dialysis.notes
                    this@DialysisViewModel.dialysis = dialysis
                }
            }
        } else {
            initialUF = 0
            finalUF = 0
            observation = ""
        }
    }

    fun addDialysis(dialysis: Dialysis) {
        viewModelScope.launch {
            dao.insertDialysis(dialysis)
        }
    }

    fun onEvent(event: AddEditDialysisEvent) {
        when (event) {
            is AddEditDialysisEvent.OnDialysisInitialUFChange -> {
                initialUF = event.initialUF
            }
            is AddEditDialysisEvent.OnDialysisFinalUFChange -> {
                finalUF = event.finalUF
            }
            is AddEditDialysisEvent.OnDialysisObservationChange -> {
                observation = event.observation
            }
            else -> {}
        }
    }

    fun updateDialysis(dialysis: Dialysis) {
        viewModelScope.launch {
            dao.updateDialysis(dialysis)
        }
    }

    fun deleteDialysis(dialysis: Dialysis) {
        viewModelScope.launch {
            dao.deleteDialysis(dialysis)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>, extras: CreationExtras
            ): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                val savedStateHandle = extras.createSavedStateHandle()
                return DialysisViewModel(
                    (application as DialysisApplication).database.dialysisDao(), savedStateHandle
                ) as T
            }
        }
    }
}
