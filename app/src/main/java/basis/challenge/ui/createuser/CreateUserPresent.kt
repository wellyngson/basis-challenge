package basis.challenge.ui.createuser

import basis.challenge.utils.commons.BasePresent

class CreateUserPresent : BasePresent<CreateUserAction, CreateUserState>() {
    override val initialState: CreateUserState
        get() = CreateUserState()

    override fun sendIntent(action: CreateUserAction) {
    }
}
