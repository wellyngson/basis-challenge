package basis.challenge.ui.di

import basis.challenge.ui.createorupdateuser.CreateOrUpdateUserPresent
import basis.challenge.ui.home.HomePresent
import basis.challenge.utils.constants.SCOPE_WITH_SUPERVISOR_JOB
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

private val presentationModule =
    module {
        factory {
            HomePresent(
                userRepository = get(),
                scope = get(named(SCOPE_WITH_SUPERVISOR_JOB)),
            )
        }

        factory {
            CreateOrUpdateUserPresent(
                userRepository = get(),
                scope = get(named(SCOPE_WITH_SUPERVISOR_JOB)),
            )
        }
    }

object PresentationModule {
    fun load() = loadKoinModules(presentationModule)
}
