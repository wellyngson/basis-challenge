package basis.challenge.di

import basis.challenge.data.database.UserDao
import basis.challenge.data.database.UserDaoImpl
import basis.challenge.data.datasource.UserLocalDataSource
import basis.challenge.data.datasource.UserLocalDataSourceImpl
import basis.challenge.data.model.AddressEntity
import basis.challenge.data.model.UserEntity
import basis.challenge.data.repository.UserRepositoryImpl
import basis.challenge.domain.repository.UserRepository
import basis.challenge.ui.home.HomePresent
import basis.challenge.utils.constants.DEFAULT_DISPATCHER
import basis.challenge.utils.constants.DEFAULT_SCOPE
import basis.challenge.utils.constants.IO_DISPATCHER
import basis.challenge.utils.constants.MAIN_DISPATCHER
import basis.challenge.utils.constants.SCOPE_WITH_SUPERVISOR_JOB
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

private val dispatcherModule =
    module {
        factory(named(DEFAULT_DISPATCHER)) { Dispatchers.Default }
        factory(named(IO_DISPATCHER)) { Dispatchers.IO }
        factory(named(MAIN_DISPATCHER)) { Dispatchers.Main }
        factory(named(DEFAULT_SCOPE)) { CoroutineScope(Dispatchers.Default) }
        factory(named(SCOPE_WITH_SUPERVISOR_JOB)) { CoroutineScope(SupervisorJob() + Dispatchers.Main) }
    }
private val dataModule =
    module {
        single {
            val config =
                RealmConfiguration.create(
                    schema = setOf(UserEntity::class, AddressEntity::class),
                )
            Realm.open(config)
        }

        single<UserDao> {
            UserDaoImpl(get())
        }

        factory<UserLocalDataSource> { UserLocalDataSourceImpl(userDao = get()) }
    }
private val domainModule =
    module {
        factory<UserRepository> {
            UserRepositoryImpl(
                userLocalDataSource = get(),
                dispatcher = get(named(IO_DISPATCHER)),
            )
        }
    }
private val presentationModule =
    module {
        factory {
            HomePresent(
                userRepository = get(),
                scope = get(named(SCOPE_WITH_SUPERVISOR_JOB)),
            )
        }
    }

object AppModule {
    fun load() = loadKoinModules(dispatcherModule + dataModule + domainModule + presentationModule)
}
