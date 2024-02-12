package com.sergiupuhalschi.presentation.ui.common.viewmodel.utils

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.sergiupuhalschi.presentation.ui.common.viewmodel.BaseViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

@Composable
inline fun <reified VM : BaseViewModel> lifecycleAwareHiltViewModel(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    qualifier: Qualifier? = null,
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    noinline parameters: ParametersDefinition? = null
): VM {
    val viewModel: VM = koinViewModel(
        qualifier = qualifier,
        viewModelStoreOwner = viewModelStoreOwner,
        parameters = parameters
    )
    ensureSingleLifecycleObserver(viewModel, lifecycleOwner)

    return viewModel
}

inline fun <reified VM : BaseViewModel> ComponentActivity.lifecycleAwareViewModel(
    lifecycleOwner: LifecycleOwner = this,
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<VM> {
    return lazy {
        val viewModel: VM by viewModel<VM>(
            qualifier = qualifier,
            parameters = parameters
        )
        ensureSingleLifecycleObserver(viewModel, lifecycleOwner)
        viewModel
    }
}

fun ensureSingleLifecycleObserver(viewModel: BaseViewModel, lifecycleOwner: LifecycleOwner) {
    if (viewModel.lifecycleHashCode != lifecycleOwner.lifecycle.hashCode()) {
        viewModel.lifecycleHashCode = lifecycleOwner.lifecycle.hashCode()
        lifecycleOwner.lifecycle.addObserver(viewModel)
    }
}