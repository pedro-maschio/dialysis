package com.pedro.solutions.dialysisnotes.views

sealed class AddEditDialysisEvent {
    data class OnDialysisInitialUFChange(val initialUF: String): AddEditDialysisEvent()
    data class OnDialysisFinalUFChange(val finalUF: String): AddEditDialysisEvent()
    data class OnDialysisObservationChange(val observation: String): AddEditDialysisEvent()
    data class OnDialysisSaved(val isEditing: Boolean): AddEditDialysisEvent()
    class OnDialysisDeleted(val id: Int?) : AddEditDialysisEvent()
}