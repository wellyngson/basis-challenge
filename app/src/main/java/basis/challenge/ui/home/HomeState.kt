package basis.challenge.ui.home

import basis.challenge.domain.model.User

data class HomeState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
)
