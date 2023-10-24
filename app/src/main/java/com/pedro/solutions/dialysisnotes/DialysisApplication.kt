package com.pedro.solutions.dialysisnotes

import android.app.Application
import com.pedro.solutions.dialysisnotes.data.dialysis.DialysisDatabase
import com.pedro.solutions.dialysisnotes.data.users.UserDatabase

class DialysisApplication: Application() {
    val dialysisDatabase: DialysisDatabase by lazy { DialysisDatabase.getDatabase(this) }
    val userDatabase: UserDatabase by lazy { UserDatabase.getDatabase(this) }
}