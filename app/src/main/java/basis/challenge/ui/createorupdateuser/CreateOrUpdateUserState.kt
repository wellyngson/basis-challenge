package basis.challenge.ui.createorupdateuser

import basis.challenge.domain.model.User

data class CreateOrUpdateUserState(
    val isLoading: Boolean = false,
    val isNewUser: Boolean = true,
    val user: User? = null,
)
