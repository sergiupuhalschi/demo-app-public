package com.sergiupuhalschi.presentation.ui.common.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.sergiupuhalschi.common.BuildConfig
import com.sergiupuhalschi.presentation.ui.app.utils.AppStateHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

open class BaseViewModel : ViewModel(), KoinComponent, DefaultLifecycleObserver {

    var lifecycleHashCode: Int = 0

    protected val appStateHandler: AppStateHandler by inject()

    protected val longRunningScope: CoroutineScope
        get() = CoroutineScope(Job())

    protected open fun processError(throwable: Throwable, overlayError: Boolean = false) {
        logError(throwable)

        viewModelScope.launch {
            appStateHandler.handleError(throwable, overlayError)
        }
    }

    protected fun logError(throwable: Throwable) {
        Timber.e(throwable)

        if (throwable is CancellationException) {
            return
        }

        recordNonFatalIssue(throwable)
    }

    protected fun recordNonFatalIssue(throwable: Throwable) {
        if (!BuildConfig.DEBUG) {
            FirebaseCrashlytics.getInstance()
                .recordException(throwable)
        }
    }

    protected fun runSafely(
        scope: CoroutineScope = viewModelScope,
        onError: (suspend (Throwable) -> Boolean)? = null,
        overlayError: Boolean = false,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return scope.launch {
            try {
                block()
            } catch (ex: CancellationException) {
                // Do nothing
            } catch (throwable: Throwable) {
                val consumed = onError?.invoke(throwable) ?: false
                if (!consumed) {
                    processError(throwable, overlayError)
                }
            }
        }
    }
}