package com.pedro.solutions.dialysisnotes.ui.pdf_generator

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.pedro.solutions.dialysisnotes.DialysisApplication
import com.pedro.solutions.dialysisnotes.data.DialysisDAO
import com.pedro.solutions.dialysisnotes.data.pdfDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PDFViewModel(
    private val dialysisDao: DialysisDAO,
    private val pdfDao: pdfDao,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddEditPDFState(isDateSelectableStartInterval = {
        true
    }, isDateSelectableEndInterval = { true }))
    val uiState: StateFlow<AddEditPDFState> = _uiState.asStateFlow()

    private val oldestDialysis = dialysisDao.getOldestDialysis()

    init {
        resetState()
    }

    fun resetState() {
        viewModelScope.launch {
            oldestDialysis.collect { oldestDialysis ->
                if (oldestDialysis.isNotEmpty()) {
                    _uiState.update {
                        AddEditPDFState(startInterval = 0,
                            endInterval = 0,
                            isDateSelectableStartInterval = {
                                it >= oldestDialysis[0] && it <= System.currentTimeMillis()
                            },
                            isDateSelectableEndInterval = {
                                it >= oldestDialysis[0] && it <= System.currentTimeMillis()
                            })
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditPDFEvent) {
        when (event) {
            is AddEditPDFEvent.OnPDFPatientChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(patient = event.patient)
                }
            }

            is AddEditPDFEvent.OnStartIntervalChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(startInterval = event.start, isDateSelectableEndInterval = {
                        // after the start date is selected, we want to update the lambda to only
                        // considers selectable the days after the start interval
                        currentState.isDateSelectableEndInterval(it) && (it >= event.start)
                    })
                }
            }

            is AddEditPDFEvent.OnFinalIntervalChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(endInterval = event.final)
                }
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
                return PDFViewModel(
                    (application as DialysisApplication).database.dialysisDao(),
                    application.database.pdfDao(),
                    savedStateHandle
                ) as T
            }
        }
    }
}