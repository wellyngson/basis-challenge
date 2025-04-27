package basis.challenge.domain.di

import basis.challenge.data.repository.UserRepositoryImpl
import basis.challenge.domain.repository.UserRepository
import basis.challenge.utils.constants.IO_DISPATCHER
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

private val domainModule =
    module {
        factory<UserRepository> {
            UserRepositoryImpl(
                userLocalDataSource = get(),
                dispatcher = get(named(IO_DISPATCHER)),
            )
        }
    }

object DomainModule {
    fun load() = loadKoinModules(domainModule)
}
