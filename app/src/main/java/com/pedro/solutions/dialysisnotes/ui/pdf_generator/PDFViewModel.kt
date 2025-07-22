package com.pedro.solutions.dialysisnotes.ui.pdf_generator

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.pedro.solutions.dialysisnotes.DialysisApplication
import com.pedro.solutions.dialysisnotes.data.dialysis.DialysisDAO
import com.pedro.solutions.dialysisnotes.data.pdf.PDF
import com.pedro.solutions.dialysisnotes.data.pdf.pdfDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import kotlin.math.max
import kotlin.math.min


class PDFViewModel(
    dialysisDao: DialysisDAO,
    private val pdfDao: pdfDao,
    private val application: DialysisApplication
) : ViewModel() {
    private val TAG = this.javaClass.simpleName

    private val _addPDFState = MutableStateFlow(AddEditPDFState(isDateSelectableStartInterval = {
        false
    }, isDateSelectableEndInterval = { false  }))
    val addPDFState: StateFlow<AddEditPDFState> = _addPDFState.asStateFlow()

    private val oldestDialysis = dialysisDao.getOldestDialysis()

    private val newestDialysis = dialysisDao.getNewestDialysis()

    private val workManager = WorkManager.getInstance(application.applicationContext)

    val allPDFsGenerated = pdfDao.getAllPDFs().asLiveData()

    init {
        resetState()
    }

    fun resetState() {
        viewModelScope.launch {
            oldestDialysis.collect { oldestDialysis ->
                newestDialysis.collect { newestDialysis ->
                    if (oldestDialysis.isNotEmpty() && newestDialysis.isNotEmpty()) {
                        _addPDFState.update {
                            AddEditPDFState(startInterval = 0,
                                endInterval = 0,
                                isDateSelectableStartInterval = {
                                    (it >= min(oldestDialysis[0], System.currentTimeMillis())) && (it <= max(newestDialysis[0], System.currentTimeMillis()))
                                },
                                isDateSelectableEndInterval = {
                                    (it >= min(oldestDialysis[0], System.currentTimeMillis())) && (it <= max(newestDialysis[0], System.currentTimeMillis()))
                                })
                        }
                    }
                }
            }
        }
    }

    fun pdfExists(uri: String?): Flow<Boolean> = flow {
        uri?.let {
            val file = File(uri)
            emit(file.exists())
        } ?: emit(false)
    }

    fun savePDF() {
        viewModelScope.launch {
            val pdf = PDF(
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                _addPDFState.value.patient,
                _addPDFState.value.startInterval,
                _addPDFState.value.endInterval,
                _addPDFState.value.fileDirectory,
                null
            )
            pdfDao.insertPDF(pdf)
        }
    }

    fun generatePDF(filePath: Uri?, patientName: String, startInterval: Long, endInterval: Long) {
        val pdfWork = OneTimeWorkRequest.Builder(GeneratePDFWorker::class.java)
        val data = Data.Builder()
        data.putString("file_path", filePath.toString())
        data.putString("patient_name", patientName)
        data.putLong("start_interval", startInterval)
        data.putLong("end_interval", endInterval)

        pdfWork.setInputData(data.build())
        workManager.enqueue(pdfWork.build())
    }

    fun onEvent(event: AddEditPDFEvent) {
        when (event) {
            is AddEditPDFEvent.OnPDFPatientChanged -> {
                _addPDFState.update { currentState ->
                    currentState.copy(patient = event.patient)
                }
            }

            is AddEditPDFEvent.OnStartIntervalChanged -> {
                _addPDFState.update { currentState ->
                    currentState.copy(startInterval = event.start, isDateSelectableEndInterval = {
                        // after the start date is selected, we want to update the lambda to only
                        // considers selectable the days after the start interval
                        currentState.isDateSelectableEndInterval(it) && (it >= event.start)
                    })
                }
            }

            is AddEditPDFEvent.OnFinalIntervalChanged -> {
                _addPDFState.update { currentState ->
                    currentState.copy(endInterval = event.final)
                }
            }

            is AddEditPDFEvent.OnDirectoryChanged -> {
                _addPDFState.update { currentState ->
                    currentState.copy(fileDirectory = event.directory)
                }
            }
        }
    }
}