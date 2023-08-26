package com.pedro.solutions.dialysisnotes

import android.app.Application
import com.pedro.solutions.dialysisnotes.database.DialysisDatabase

class DialysisApplication: Application() {
    val database: DialysisDatabase by lazy { DialysisDatabase.getDatabase(this) }
}