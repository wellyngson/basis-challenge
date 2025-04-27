package basis.challenge.ui.createorupdateuser

import basis.challenge.domain.model.Address
import basis.challenge.domain.model.User
import basis.challenge.domain.repository.UserRepository
import basis.challenge.utils.commons.BasePresent
import basis.challenge.utils.enums.PersonTypeEnum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CreateOrUpdateUserPresent(
    private val userRepository: UserRepository,
    private val scope: CoroutineScope,
) : BasePresent<CreateOrUpdateUserAction, CreateOrUpdateUserResult, CreateOrUpdateUserState>(scope) {
    override val initialState: CreateOrUpdateUserState
        get() = CreateOrUpdateUserState()

    override fun sendIntent(action: CreateOrUpdateUserAction) {
        when (action) {
            is CreateOrUpdateUserAction.InitializeUser -> initializeUser(action.user, action.isNewUser)
            is CreateOrUpdateUserAction.CreateUser -> createUser(action.user)
            is CreateOrUpdateUserAction.UpdateUser -> updateUser(action.user)
            is CreateOrUpdateUserAction.UpdatePersonType -> addPersonTypeInUser(action.personType)
            is CreateOrUpdateUserAction.AddAddressInUser -> addAddressInUser(action.address)
            is CreateOrUpdateUserAction.UpdateAddressInUser -> updateAddressInUser(action.address)
            is CreateOrUpdateUserAction.RemoveAddressOfUser -> removeAddInUser(action.address)
        }
    }

    private fun initializeUser(
        user: User,
        isNewUser: Boolean,
    ) {
        updateUiState(uiState.value.copy(user = user, isNewUser = isNewUser))
    }

    private fun createUser(user: User) {
        scope.launch {
            updateUiState(uiState.value.copy(isLoading = true))
            userRepository.createUser(user)
            updateUiState(uiState.value.copy(isLoading = false, user = user))
            emitResult(CreateOrUpdateUserResult.UserCreatedWithSuccess)
        }
    }

    private fun updateUser(user: User) {
        scope.launch {
            updateUiState(uiState.value.copy(isLoading = true))
            userRepository.updateUser(user)
            updateUiState(uiState.value.copy(isLoading = false, user = user))
            emitResult(CreateOrUpdateUserResult.UserUpdatedWithSuccess)
        }
    }

    private fun addPersonTypeInUser(personType: PersonTypeEnum) {
        scope.launch {
            val newUser = uiState.value.user?.copy(personType = personType)
            updateUiState(uiState.value.copy(user = newUser))
        }
    }

    private fun addAddressInUser(address: Address) {
        scope.launch {
            val newAddress =
                uiState.value.user
                    ?.addresses
                    ?.plus(address) ?: emptyList()
            val newUser = uiState.value.user?.copy(addresses = newAddress)
            updateUiState(uiState.value.copy(user = newUser))
        }
    }

    private fun updateAddressInUser(address: Address) {
        scope.launch {
            val newAddress =
                uiState.value.user
                    ?.addresses
                    ?.map { if (it.id == address.id) address else it } ?: emptyList()
            val newUser = uiState.value.user?.copy(addresses = newAddress)
            updateUiState(uiState.value.copy(user = newUser))
        }
    }

    private fun removeAddInUser(address: Address) {
        scope.launch {
            val newAddress =
                uiState.value.user
                    ?.addresses
                    ?.filterNot { it.id == address.id } ?: emptyList()
            val newUser = uiState.value.user?.copy(addresses = newAddress)
            updateUiState(uiState.value.copy(user = newUser))
        }
    }
}
