package basis.challenge

import basis.challenge.domain.model.User
import basis.challenge.domain.repository.UserRepository
import basis.challenge.ui.home.HomeAction
import basis.challenge.ui.home.HomePresent
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomePresentTest {
    private val userRepository: UserRepository = mockk(relaxed = true)
    private val testDispatcher = UnconfinedTestDispatcher()

    private fun setupHomePresent(scope: CoroutineScope) =
        HomePresent(
            userRepository = userRepository,
            scope = scope,
        )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `users should be updated when repositories getUsers is called and returns a list users`() =
        runTest(testDispatcher) {
            // prepare
            val present = setupHomePresent(this)
            val result = listOf(User.initializeEmptyUser())
            // when
            coEvery { userRepository.getUsers() } returns flowOf(result)
            present.sendIntent(HomeAction.GetUsers)
            // then
            assertEquals(present.uiState.value.users, result)
        }

    @Test
    fun `usersFiltered should be updated when repositories getUsers is called and returns a list users`() =
        runTest(testDispatcher) {
            // prepare
            val present = setupHomePresent(this)
            val result = listOf(User.initializeEmptyUser())
            // when
            coEvery { userRepository.getUsers() } returns flowOf(result)
            present.sendIntent(HomeAction.GetUsers)
            // then
            assertEquals(present.uiState.value.usersFiltered, result)
        }

    @Test
    fun `user should be remover in uiState when repositories deleteUser is called and returns success`() =
        runTest(testDispatcher) {
            // prepare
            val present = setupHomePresent(this)
            val initialUsers = listOf(User.initializeEmptyUser(), User.initializeEmptyUser())
            // when
            present.sendIntent(HomeAction.DeleteUser(initialUsers.first()))
            // then
            assertEquals(
                present.uiState.value.users
                    .contains(initialUsers.first()),
                false,
            )
        }

    @Test
    fun `user two should be removed when filter is writing`() =
        runTest(testDispatcher) {
            // prepare
            val present = setupHomePresent(this)
            val filter = "Test 01"
            val firstUser = User.initializeEmptyUser().copy(name = "Test 01")
            val secondUser = User.initializeEmptyUser().copy(name = "Test 02")
            val initialUsers =
                listOf(
                    firstUser,
                    secondUser,
                )
            // when
            coEvery { userRepository.getUsers() } returns flowOf(initialUsers)
            present.sendIntent(HomeAction.GetUsers)
            present.sendIntent(HomeAction.FilterUsers(filter))
            // then
            assertEquals(
                present.uiState.value.usersFiltered
                    .contains(secondUser),
                false,
            )
        }
}
