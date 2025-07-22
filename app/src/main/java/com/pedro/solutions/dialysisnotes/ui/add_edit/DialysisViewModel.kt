package com.pedro.solutions.dialysisnotes.ui.add_edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.pedro.solutions.dialysisnotes.DialysisApplication
import com.pedro.solutions.dialysisnotes.data.dialysis.Dialysis
import com.pedro.solutions.dialysisnotes.data.dialysis.DialysisDAO
import com.pedro.solutions.dialysisnotes.ui.Utils
import com.pedro.solutions.dialysisnotes.utils.DataTestGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DialysisViewModel(
    private val dao: DialysisDAO
) : ViewModel() {
    val dialysisList: LiveData<List<Dialysis>> = dao.getAllDialysisNewestFirst().asLiveData()

    private val _uiState = MutableStateFlow(AddEditDialysisState())
    val uiState: StateFlow<AddEditDialysisState> = _uiState.asStateFlow()

    init {
//        viewModelScope.launch {
//            DataTestGenerator.generateMediumDialysisList().forEach { dao.insertDialysis(it) }
//        }
        resetState()
    }

    private fun resetState() {
        _uiState.update { AddEditDialysisState(System.currentTimeMillis()) }
    }

    fun loadDialysisItem(id: Int?) {
        id?.let {
            if (id > -1) {
                viewModelScope.launch {
                    val d = dao.findDialysisById(id)
                    d?.let {
                        _uiState.update {
                            AddEditDialysisState(
                                d.createdAt,
                                d.initialUf.toString(),
                                d.finalUf.toString(),
                                d.notes,
                                true,
                                id
                            )
                        }
                    }
                }
            } else {
                resetState()
            }
        }
    }

    private fun saveDialysis(isEditing: Boolean) {
        viewModelScope.launch {
            val d = Dialysis(
                _uiState.value.createdAt,
                System.currentTimeMillis(),
                _uiState.value.initialUF.toInt(),
                _uiState.value.finalUF.toInt(),
                _uiState.value.observations,
                _uiState.value.id
            )
            if (isEditing) dao.updateDialysis(d)
            else dao.insertDialysis(d)
        }
    }

    /* TODO: how to create a composable data class value (isSaveButtonEnabled)? It needs to depends on another invalida attributes
    */
    fun onEvent(event: AddEditDialysisEvent) {
        when (event) {
            is AddEditDialysisEvent.OnDialysisInitialUFChange -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        initialUF = event.initialUF,
                        initialUFInvalid = !Utils.isStringInt(event.initialUF)
                    )
                }
            }

            is AddEditDialysisEvent.OnDialysisFinalUFChange -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        finalUF = event.finalUF, finalUFInvalid = !Utils.isStringInt(event.finalUF)
                    )
                }

            }

            is AddEditDialysisEvent.OnDialysisObservationChange -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        observations = event.observation,
                        observationsInvalid = event.observation.length > Utils.MAX_OBSERVATIONS_CHARACTERS_SIZE
                    )
                }
            }

            is AddEditDialysisEvent.OnDialysisSaved -> {
                saveDialysis(event.isEditing)
            }

            is AddEditDialysisEvent.OnDialysisDeleted -> {
                deleteDialysis(event.id)
            }

            is AddEditDialysisEvent.OnDialysisListDeleted -> {
                event.toDeleteItems.forEach {
                    deleteDialysis(it)
                }
            }
        }
    }

    private fun deleteDialysis(id: Int?) {
        id?.let {
            viewModelScope.launch {
                dao.deleteDialysis(id)
            }
        }
    }
}
