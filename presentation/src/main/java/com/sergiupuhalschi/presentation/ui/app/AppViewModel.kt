package com.sergiupuhalschi.presentation.ui.app

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.sergiupuhalschi.common.utils.NetworkConnectivityHandler
import com.sergiupuhalschi.presentation.ui.app.models.AppEventState
import com.sergiupuhalschi.presentation.ui.common.navigation.NavigationRoute
import com.sergiupuhalschi.presentation.ui.common.viewmodel.BaseViewModel
import com.sergiupuhalschi.repository.account.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

class AppViewModel(
    private val accountRepository: AccountRepository,
    private val networkConnectivityHandler: NetworkConnectivityHandler
) : BaseViewModel() {

    private val navigationStartDestination = MutableStateFlow<NavigationRoute?>(null)
    private val eventState = MutableStateFlow<AppEventState>(AppEventState.Idle)

    init {
        initSession()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        networkConnectivityHandler.startConnectivityListening(owner.lifecycleScope)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        networkConnectivityHandler.stopConnectivityListening()
    }

    fun navigationStartDestination(): StateFlow<NavigationRoute?> = navigationStartDestination

    fun eventState(): StateFlow<AppEventState> = eventState

    fun acknowledgeEventState() {
        viewModelScope.launch { eventState.emit(AppEventState.Idle) }
    }

    private fun initSession() {
        runSafely {
            val isLoggedIn = accountRepository.isSignedIn()
            navigationStartDestination.emit(if (isLoggedIn) NavigationRoute.Main else NavigationRoute.Onboarding)

            observeSessionUpdates()
        }
    }

    private fun observeSessionUpdates() {
        viewModelScope.launch {
            accountRepository.observeSignedIn()
                .drop(1)
                .distinctUntilChanged()
                .collect { loggedIn ->
                    if (loggedIn) {
                        initUserInfo()
                        eventState.emit(
                            AppEventState.Navigate(
                                route = NavigationRoute.Main,
                                popUpRoute = NavigationRoute.Onboarding
                            )
                        )
                    } else {
                        eventState.emit(
                            AppEventState.Navigate(
                                route = NavigationRoute.Onboarding,
                                popUpRoute = NavigationRoute.Main
                            )
                        )
                    }
                }
        }
    }

    private fun initUserInfo() {
        //TODO("Not Implemented")
    }
}