package com.dhandev.expenseeye.di

import com.dhandev.expenseeye.presentation.landing.LandingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { LandingViewModel(get()) }
}