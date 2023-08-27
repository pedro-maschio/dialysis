package com.pedro.solutions.dialysisnotes.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.navigation.NavArgument
import com.pedro.solutions.dialysisnotes.DialysisApplication
import com.pedro.solutions.dialysisnotes.model.Dialysis
import com.pedro.solutions.dialysisnotes.model.DialysisDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DialysisViewModel(
    private val dao: DialysisDAO, private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val dialysisList: LiveData<List<Dialysis>> = dao.getAllDialysis().asLiveData()
    private val dialysisId: Int? = savedStateHandle["userId"]
     val dialysisItem: Flow<Dialysis?>? = dao.findDialysisById(dialysisId)

    fun setCurrentDialysis(id: Int) {
        Log.d("PEDRO123", "id=$id")
        savedStateHandle["userId"] = id
    }

    fun addDialysis(dialysis: Dialysis) {
        viewModelScope.launch {
            dao.insertDialysis(dialysis)
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
