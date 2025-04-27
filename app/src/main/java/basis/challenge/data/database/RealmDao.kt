package basis.challenge.data.database

import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import kotlin.reflect.KClass

interface RealmDao<T : RealmObject> {
    val realm: Realm
    val clazz: KClass<T>

    suspend fun insert(entity: T) {
        realm.write {
            copyToRealm(entity)
        }
    }

    suspend fun insertAll(entities: List<T>) {
        realm.write {
            for (entity in entities) {
                copyToRealm(entity)
            }
        }
    }

    suspend fun update(entity: T) {
        realm.write {
            copyToRealm(entity, updatePolicy = UpdatePolicy.ALL)
        }
    }

    suspend fun findAll(): RealmResults<T> = realm.query(clazz).find()

    suspend fun findById(id: ObjectId): T? = realm.query(clazz, "_id == $0", id).first().find()

    suspend fun delete(id: ObjectId) {
        val managedEntity = findById(id)

        realm.write {
            val liveEntity = managedEntity?.let { findLatest(it) }

            liveEntity?.let {
                delete(it)
            }
        }
    }

    suspend fun stream(): Flow<ResultsChange<T>> = realm.query(clazz).asFlow()

    suspend fun deleteAll() {
        realm.write {
            val all = this.query(clazz).find()
            delete(all)
        }
    }
}
