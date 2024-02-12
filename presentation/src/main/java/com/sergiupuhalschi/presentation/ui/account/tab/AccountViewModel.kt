package com.sergiupuhalschi.presentation.ui.account.tab

import androidx.lifecycle.viewModelScope
import com.sergiupuhalschi.presentation.ui.account.tab.models.AccountEventState
import com.sergiupuhalschi.presentation.ui.account.tab.models.AccountViewState
import com.sergiupuhalschi.presentation.ui.common.viewmodel.BaseViewModel
import com.sergiupuhalschi.repository.account.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountViewModel(
    private val accountRepository: AccountRepository
) : BaseViewModel() {

    private val viewState = MutableStateFlow(AccountViewState())
    private val eventState = MutableStateFlow<AccountEventState>(AccountEventState.Idle)

    init {
        initData()
    }

    fun viewState(): StateFlow<AccountViewState> = viewState

    fun eventState(): StateFlow<AccountEventState> = eventState

    fun onSignOutClick() {
        viewModelScope.launch { eventState.emit(AccountEventState.SignOutConfirmation) }
    }

    fun signOut() {
        acknowledgeEvent()

        runSafely {
            accountRepository.signOut()
        }
    }

    fun acknowledgeEvent() {
        viewModelScope.launch { eventState.emit(AccountEventState.Idle) }
    }

    private fun initData() {
        runSafely {
            val account = accountRepository.getAccount()

            viewState.emit(
                AccountViewState(
                    name = account.name,
                    imageUrl = account.imageUrl
                )
            )
        }
    }
}