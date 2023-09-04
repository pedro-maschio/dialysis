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
import com.pedro.solutions.dialysisnotes.data.Dialysis
import com.pedro.solutions.dialysisnotes.data.DialysisDAO
import com.pedro.solutions.dialysisnotes.ui.Utils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DialysisViewModel(
    private val dao: DialysisDAO, private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val dialysisList: LiveData<List<Dialysis>> = dao.getAllDialysis().asLiveData()

    private val _uiState = MutableStateFlow(AddEditDialysisState())
    val uiState: StateFlow<AddEditDialysisState> = _uiState.asStateFlow()

    init {
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
                        finalUF = event.finalUF,
                        finalUFInvalid = !Utils.isStringInt(event.finalUF)
                    )
                }

            }

            is AddEditDialysisEvent.OnDialysisObservationChange -> {
                _uiState.update { currentState -> currentState.copy(observations = event.observation) }
            }

            is AddEditDialysisEvent.OnDialysisSaved -> {
                saveDialysis(event.isEditing)
            }

            is AddEditDialysisEvent.OnDialysisDeleted -> {
                deleteDialysis(event.id)
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
