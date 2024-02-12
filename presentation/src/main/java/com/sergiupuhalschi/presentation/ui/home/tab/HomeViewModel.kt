package com.sergiupuhalschi.presentation.ui.home.tab

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sergiupuhalschi.presentation.ui.common.viewmodel.BaseViewModel
import com.sergiupuhalschi.presentation.ui.home.tab.mappers.PersonViewDataMapper
import com.sergiupuhalschi.presentation.ui.home.tab.models.HomeEventState
import com.sergiupuhalschi.presentation.ui.home.tab.models.HomeViewState
import com.sergiupuhalschi.repository.person.PersonRepository
import com.sergiupuhalschi.repository.person.models.Person
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val personRepository: PersonRepository,
    private val personViewDataMapper: PersonViewDataMapper
) : BaseViewModel() {

    private val viewState = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    private val eventState = MutableStateFlow<HomeEventState>(HomeEventState.Idle)

    init {
        initData()
    }

    fun viewState(): StateFlow<HomeViewState> = viewState

    fun eventState(): StateFlow<HomeEventState> = eventState

    fun acknowledgeEvent() {
        viewModelScope.launch { eventState.emit(HomeEventState.Idle) }
    }

    fun refresh() {
        runSafely(
            onError = {
                acknowledgeEvent()
                false
            }
        ) {
            eventState.emit(HomeEventState.Refreshing)
            val items = personRepository.fetchPersons()
            onDataAvailable(items)
            acknowledgeEvent()
        }
    }

    private fun initData() {
        viewModelScope.launch {
            personRepository.getPersons()
                .catch { processError(it) }
                .collect { onDataAvailable(it) }
        }
    }

    private suspend fun onDataAvailable(items: List<Person>) {
        viewState.emit(
            if (items.isEmpty()) {
                HomeViewState.Empty
            } else {
                HomeViewState.Data(
                    items = personViewDataMapper.from(items)
                )
            }
        )
    }
}