package basis.challenge

import basis.challenge.domain.model.Address
import basis.challenge.domain.model.User
import basis.challenge.domain.repository.UserRepository
import basis.challenge.ui.createorupdateuser.CreateOrUpdateUserAction
import basis.challenge.ui.createorupdateuser.CreateOrUpdateUserPresent
import basis.challenge.utils.enums.PersonTypeEnum
import io.mockk.MockKAnnotations
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CreateOrUpdateUserPresentTest {
    private val userRepository: UserRepository = mockk(relaxed = true)
    private val testDispatcher = UnconfinedTestDispatcher()

    private fun setupCreateOrUpdatePresent(scope: CoroutineScope) =
        CreateOrUpdateUserPresent(
            userRepository = userRepository,
            scope = scope,
        )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `users should be updated when initializeUsers is called`() =
        runTest(testDispatcher) {
            // prepare
            val present = setupCreateOrUpdatePresent(this)
            val user = User.initializeEmptyUser()
            // when
            present.sendIntent(CreateOrUpdateUserAction.InitializeUser(user, false))
            // then
            assertEquals(present.uiState.value.user, user)
            assertEquals(present.uiState.value.isNewUser, false)
        }

    @Test
    fun `user should be created when repository createUser is called and return success`() =
        runTest(testDispatcher) {
            // prepare
            val present = setupCreateOrUpdatePresent(this)
            val user = User.initializeEmptyUser()
            // when
            present.sendIntent(CreateOrUpdateUserAction.CreateUser(user))
            // then
            assertEquals(present.uiState.value.user, user)
            assertEquals(present.uiState.value.isNewUser, true)
        }

    @Test
    fun `user should be updated when repository updateUser is called and return success`() =
        runTest(testDispatcher) {
            // prepare
            val present = setupCreateOrUpdatePresent(this)
            val user = User.initializeEmptyUser()
            // when
            present.sendIntent(CreateOrUpdateUserAction.UpdateUser(user))
            // then
            assertEquals(present.uiState.value.user, user)
            assertEquals(present.uiState.value.isNewUser, true)
        }

    @Test
    fun `user should be updaxcxcted when repository updateUser is called and return success`() =
        runTest(testDispatcher) {
            // prepare
            val present = setupCreateOrUpdatePresent(this)
            val user = User.initializeEmptyUser()
            val newPersonType = PersonTypeEnum.COMPANY
            // when
            present.sendIntent(CreateOrUpdateUserAction.InitializeUser(user, true))
            present.sendIntent(CreateOrUpdateUserAction.UpdatePersonType(newPersonType))
            // then
            assertEquals(
                present.uiState.value.user
                    ?.personType,
                newPersonType,
            )
        }

    @Test
    fun `address should be updated when addAddressInUser is called`() =
        runTest(testDispatcher) {
            // prepare
            val present = setupCreateOrUpdatePresent(this)
            val user = User.initializeEmptyUser()
            val address = Address.initializeEmptyAddress()
            // when
            present.sendIntent(CreateOrUpdateUserAction.InitializeUser(user, true))
            present.sendIntent(CreateOrUpdateUserAction.AddAddressInUser(address))
            // then
            assertEquals(
                present.uiState.value.user
                    ?.addresses
                    ?.first(),
                address,
            )
        }
}
