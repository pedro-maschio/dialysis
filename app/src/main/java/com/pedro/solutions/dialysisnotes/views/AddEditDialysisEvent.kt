package com.pedro.solutions.dialysisnotes.views

sealed class AddEditDialysisEvent {
    data class OnDialysisInitialUFChange(val initialUF: Int): AddEditDialysisEvent()
    data class OnDialysisFinalUFChange(val finalUF: Int): AddEditDialysisEvent()
    data class OnDialysisObservationChange(val observation: String): AddEditDialysisEvent()
}