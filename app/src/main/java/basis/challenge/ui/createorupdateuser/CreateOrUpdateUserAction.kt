package basis.challenge.ui.createorupdateuser

import basis.challenge.domain.model.Address
import basis.challenge.domain.model.User
import basis.challenge.utils.enums.PersonTypeEnum

interface CreateOrUpdateUserAction {
    data class InitializeUser(
        val user: User,
        val isNewUser: Boolean,
    ) : CreateOrUpdateUserAction

    data class CreateUser(
        val user: User,
    ) : CreateOrUpdateUserAction

    data class UpdateUser(
        val user: User,
    ) : CreateOrUpdateUserAction

    data class AddAddressInUser(
        val address: Address,
    ) : CreateOrUpdateUserAction

    data class RemoveAddressOfUser(
        val address: Address,
    ) : CreateOrUpdateUserAction

    data class UpdatePersonType(
        val personType: PersonTypeEnum,
    ) : CreateOrUpdateUserAction
}
