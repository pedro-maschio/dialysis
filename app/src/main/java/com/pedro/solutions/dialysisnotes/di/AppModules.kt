package com.pedro.solutions.dialysisnotes.di

import androidx.room.Room
import com.pedro.solutions.dialysisnotes.DialysisApplication
import com.pedro.solutions.dialysisnotes.data.dialysis.DialysisDatabase
import com.pedro.solutions.dialysisnotes.data.users.UserDatabase
import com.pedro.solutions.dialysisnotes.ui.dialysis.DialysisViewModel
import com.pedro.solutions.dialysisnotes.ui.login.LoginViewModel
import com.pedro.solutions.dialysisnotes.ui.pdf.PDFViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val AppModules = module {
    single {
        Room.databaseBuilder(androidApplication(), DialysisDatabase::class.java, "dialysis_database").build()
    }
    single {
        Room.databaseBuilder(androidApplication(), UserDatabase::class.java, "user_database").build()
    }

    single {
        val dialysisDatabase = get<DialysisDatabase>()
        dialysisDatabase.dialysisDao()
    }

    single {
        val dialysisDatabase = get<DialysisDatabase>()
        dialysisDatabase.pdfDao()
    }

    single {
        val userDatabase = get<UserDatabase>()
        userDatabase.userDao()
    }

    single {
         androidApplication() as DialysisApplication
    }
    viewModel { DialysisViewModel(get()) }
    viewModel { PDFViewModel(get(), get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
}