package com.sergiupuhalschi.presentation.common.di

import com.sergiupuhalschi.presentation.ui.app.AppViewModel
import com.sergiupuhalschi.presentation.ui.account.tab.AccountViewModel
import com.sergiupuhalschi.presentation.ui.home.tab.HomeViewModel
import com.sergiupuhalschi.presentation.ui.onboarding.welcome.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val viewModelsModule = module {

    viewModelOf(::AppViewModel)

    viewModelOf(::WelcomeViewModel)

    viewModelOf(::HomeViewModel)

    viewModelOf(::AccountViewModel)
}