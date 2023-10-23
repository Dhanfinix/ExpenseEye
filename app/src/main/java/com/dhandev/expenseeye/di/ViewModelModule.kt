package com.dhandev.expenseeye.di

import com.dhandev.expenseeye.presentation.landing.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val ViewModelModule = module {
    viewModelOf(::MainViewModel)
}