package basis.challenge.data.database

import basis.challenge.data.model.UserEntity
import io.realm.kotlin.Realm
import kotlin.reflect.KClass

interface UserDao : RealmDao<UserEntity>

class UserDaoImpl(
    private val r: Realm,
) : UserDao {
    override val realm: Realm
        get() = r
    override val clazz: KClass<UserEntity>
        get() = UserEntity::class
}
