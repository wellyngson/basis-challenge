package basis.challenge.utils.commons

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BasePresent<ACTION, STATE> {
    private val _uiState: MutableStateFlow<STATE> by lazy { MutableStateFlow(initialState) }
    val uiState: StateFlow<STATE> = _uiState
    protected abstract val initialState: STATE

    abstract fun sendIntent(action: ACTION)

    protected fun updateUiState(uiState: STATE) {
        _uiState.value = uiState
    }
}
