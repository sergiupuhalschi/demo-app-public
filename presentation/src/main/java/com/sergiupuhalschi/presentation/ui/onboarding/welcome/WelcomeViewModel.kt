package com.sergiupuhalschi.presentation.ui.onboarding.welcome

import androidx.lifecycle.viewModelScope
import com.sergiupuhalschi.presentation.ui.common.viewmodel.BaseViewModel
import com.sergiupuhalschi.presentation.ui.onboarding.welcome.models.WelcomeEventState
import com.sergiupuhalschi.repository.account.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val accountRepository: AccountRepository
) : BaseViewModel() {

    private val eventState = MutableStateFlow<WelcomeEventState>(WelcomeEventState.Idle)

    fun eventState(): StateFlow<WelcomeEventState> = eventState

    fun onSignInClick() {
        viewModelScope.launch { eventState.emit(WelcomeEventState.SignIn) }
    }

    fun onSignInSuccess() {
        acknowledgeEvent()
        runSafely { accountRepository.onSignedIn() }
    }

    fun onSignInFailed(throwable: Throwable) {
        processError(throwable)
        acknowledgeEvent()
    }

    fun acknowledgeEvent() {
        viewModelScope.launch { eventState.emit(WelcomeEventState.Idle) }
    }
}