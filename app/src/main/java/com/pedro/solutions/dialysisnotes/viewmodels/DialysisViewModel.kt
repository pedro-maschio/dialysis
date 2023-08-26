package com.pedro.solutions.dialysisnotes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.pedro.solutions.dialysisnotes.DialysisApplication
import com.pedro.solutions.dialysisnotes.model.Dialysis
import com.pedro.solutions.dialysisnotes.model.DialysisDAO

class DialysisViewModel(private val dao: DialysisDAO, private val savedStateHandle: SavedStateHandle) : ViewModel() {
    val dialysisList: LiveData<List<Dialysis>> = dao.getAllDialysis().asLiveData()

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                val savedStateHandle = extras.createSavedStateHandle()
                return DialysisViewModel((application as DialysisApplication).database.dialysisDao(), savedStateHandle) as T
            }
        }
    }
}
