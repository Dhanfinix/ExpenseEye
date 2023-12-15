package com.dhandev.rekapin.di

import com.dhandev.rekapin.presentation.create.CreateViewModel
import com.dhandev.rekapin.presentation.landing.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { CreateViewModel(get(), get()) }
}