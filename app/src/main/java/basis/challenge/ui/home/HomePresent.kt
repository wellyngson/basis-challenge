package basis.challenge.ui.home

import basis.challenge.domain.model.User
import basis.challenge.domain.repository.UserRepository
import basis.challenge.utils.commons.BasePresent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

internal class HomePresent(
    private val userRepository: UserRepository,
    private val scope: CoroutineScope,
) : BasePresent<HomeAction, HomeResult, HomeState>(scope) {
    override val initialState: HomeState
        get() = HomeState()

    init {
        sendIntent(HomeAction.GetUsers)
    }

    override fun sendIntent(action: HomeAction) {
        when (action) {
            is HomeAction.GetUsers -> getUsers()
            is HomeAction.DeleteUser -> deleteUser(action.user)
            is HomeAction.FilterUsers -> filterUsers(action.filter)
        }
    }

    private fun getUsers() {
        scope.launch {
            userRepository
                .getUsers()
                .onStart { updateUiState(uiState.value.copy(isLoading = true)) }
                .collect {
                    updateUiState(
                        uiState.value.copy(
                            isLoading = false,
                            users = it,
                            usersFiltered = it,
                        ),
                    )
                }
        }
    }

    private fun deleteUser(user: User) {
        scope.launch {
            updateUiState(uiState.value.copy(isLoading = true))
            userRepository.deleteUser(user)
            val newUsers = uiState.value.users - user
            updateUiState(
                uiState.value.copy(
                    isLoading = false,
                    users = newUsers,
                    usersFiltered = newUsers,
                ),
            )
            emitResult(HomeResult.UserDeletedWithSuccess)
        }
    }

    private fun filterUsers(filter: String?) {
        scope.launch {
            val users = uiState.value.users
            val filteredUsers =
                if (filter.isNullOrEmpty()) {
                    users
                } else {
                    users.filter {
                        it.name.contains(filter, ignoreCase = true) ||
                            it.document.contains(filter, ignoreCase = true) ||
                            it.email?.contains(filter, ignoreCase = true) == true ||
                            it.phone.contains(filter, ignoreCase = true)
                    }
                }

            updateUiState(
                uiState.value.copy(
                    usersFiltered = filteredUsers,
                ),
            )
        }
    }
}
