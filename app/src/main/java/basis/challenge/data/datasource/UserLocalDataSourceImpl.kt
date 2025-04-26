package basis.challenge.data.datasource

import basis.challenge.data.database.UserDao
import basis.challenge.data.model.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserLocalDataSourceImpl(
    private val userDao: UserDao,
) : UserLocalDataSource {
    override fun getUsers(): Flow<List<UserEntity>> =
        flow {
            emit(userDao.findAll())
        }
}
