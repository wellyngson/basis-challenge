package basis.challenge.data.repository

import basis.challenge.data.datasource.UserLocalDataSource
import basis.challenge.domain.model.User
import basis.challenge.domain.model.User.Companion.toUi
import basis.challenge.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
    private val dispatcher: CoroutineDispatcher,
) : UserRepository {
    override fun getUsers(): Flow<List<User>> =
        flow {
            userLocalDataSource.getUsers().collect { users ->
                emit(users.map { it.toUi() })
            }
        }.flowOn(dispatcher)
}
