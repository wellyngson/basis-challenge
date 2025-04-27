package basis.challenge.ui.home

import basis.challenge.domain.model.User

interface HomeAction {
    data object GetUsers : HomeAction

    data class DeleteUser(
        val user: User,
    ) : HomeAction

    data class FilterUsers(
        val filter: String?,
    ) : HomeAction
}
