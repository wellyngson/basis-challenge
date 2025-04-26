package basis.challenge.data.datasource

import basis.challenge.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    fun getUsers(): Flow<List<UserEntity>>
}
