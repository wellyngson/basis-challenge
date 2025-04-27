package basis.challenge.ui.home

import basis.challenge.domain.model.User

internal interface HomeAction {
    data object GetUsers : HomeAction

    data class DeleteUser(
        val user: User,
    ) : HomeAction
}
