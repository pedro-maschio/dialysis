package com.pedro.solutions.dialysisnotes.ui.add_edit

sealed class AddEditDialysisEvent {
    data class OnDialysisInitialUFChange(val initialUF: String) : AddEditDialysisEvent()
    data class OnDialysisFinalUFChange(val finalUF: String) : AddEditDialysisEvent()
    data class OnDialysisObservationChange(val observation: String) : AddEditDialysisEvent()
    data class OnDialysisSaved(val isEditing: Boolean) : AddEditDialysisEvent()
    data class OnDialysisDeleted(val id: Int?) : AddEditDialysisEvent()
    data class OnDialysisListDeleted(val toDeleteItems: List<Int?>) : AddEditDialysisEvent()
}