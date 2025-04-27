package basis.challenge

import android.app.Application
import basis.challenge.data.di.DataModule
import basis.challenge.domain.di.DomainModule
import basis.challenge.ui.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidContext(this@AppApplication)
            loadKoinModules()
        }
    }

    private fun loadKoinModules() {
        DataModule.load()
        DomainModule.load()
        PresentationModule.load()
    }
}
