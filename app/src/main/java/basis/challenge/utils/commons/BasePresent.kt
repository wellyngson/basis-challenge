package basis.challenge.utils.commons

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BasePresent<ACTION, RESULT, STATE>(
    private val scope: CoroutineScope,
) {
    private val _uiState: MutableStateFlow<STATE> by lazy { MutableStateFlow(initialState) }
    val uiState: StateFlow<STATE> = _uiState
    private val _result = MutableSharedFlow<RESULT>()
    val result: SharedFlow<RESULT> = _result
    protected abstract val initialState: STATE

    abstract fun sendIntent(action: ACTION)

    protected fun emitResult(result: RESULT) =
        scope.launch {
            _result.emit(result)
        }

    protected fun updateUiState(uiState: STATE) {
        _uiState.value = uiState
    }
}
