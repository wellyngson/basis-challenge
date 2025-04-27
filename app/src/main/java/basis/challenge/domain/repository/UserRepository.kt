package basis.challenge.domain.repository

import basis.challenge.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<List<User>>

    suspend fun createUser(user: User)

    suspend fun deleteUser(user: User)

    suspend fun updateUser(user: User)
}
