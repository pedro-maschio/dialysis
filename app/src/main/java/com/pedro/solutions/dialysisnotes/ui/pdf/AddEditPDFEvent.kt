package com.pedro.solutions.dialysisnotes.ui.pdf

sealed class AddEditPDFEvent {
    data class OnPDFPatientChanged(val patient: String): AddEditPDFEvent()
    data class OnStartIntervalChanged(val start: Long): AddEditPDFEvent()
    data class OnFinalIntervalChanged(val final: Long): AddEditPDFEvent()
    data class OnDirectoryChanged(val directory: String): AddEditPDFEvent()
}