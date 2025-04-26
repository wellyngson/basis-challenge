package basis.challenge.ui.home

import basis.challenge.domain.repository.UserRepository
import basis.challenge.utils.commons.BasePresent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

internal class HomePresent(
    private val userRepository: UserRepository,
    private val scope: CoroutineScope,
) : BasePresent<HomeAction, HomeState>() {
    override val initialState: HomeState
        get() = HomeState()

    init {
        sendIntent(HomeAction.GetUsers)
    }

    override fun sendIntent(action: HomeAction) {
        when (action) {
            is HomeAction.GetUsers -> getUsers()
        }
    }

    private fun getUsers() {
        userRepository
            .getUsers()
            .onStart { updateUiState(uiState.value.copy(isLoading = true)) }
            .onEach { updateUiState(uiState.value.copy(isLoading = false, users = it)) }
            .launchIn(scope)
    }
}
