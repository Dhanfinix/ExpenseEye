package com.dhandev.expenseeye.di

import com.dhandev.expenseeye.presentation.create.CreateViewModel
import com.dhandev.expenseeye.presentation.landing.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { CreateViewModel(get()) }
}