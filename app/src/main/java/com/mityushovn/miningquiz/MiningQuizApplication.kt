package com.mityushovn.miningquiz

import android.app.Application
import androidx.work.*
import com.mityushovn.miningquiz.di.components.AppComponent
import com.mityushovn.miningquiz.di.components.DaggerAppComponent
import com.mityushovn.miningquiz.module_injector.extensions.DepsMap
import com.mityushovn.miningquiz.module_injector.interfaces.DependenciesProvider
import com.mityushovn.miningquiz.work.ReminderWorker
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @REMINDER_REPEAT_INTERVAL represents time duration to the next reminder notification.
 * @see TimeUnit
 */
private const val REMINDER_REPEAT_INTERVAL = 7L

/**
 * @UNIQUE_WORK_NAME
 * Unique work name for the workManager.enqueueUniquePeriodicWork() call.
 */
private const val UNIQUE_WORK_NAME = "REMINDER TO PLAY"

class MiningQuizApplication : Application(), DependenciesProvider, Configuration.Provider {

    lateinit var appComponent: AppComponent
        private set

    @Inject
    override lateinit var depsMap: DepsMap

    override fun onCreate() {
        super.onCreate()
        // 1) init Timber logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        // 2) init Dagger AppComponent
        /**
         * @see AppComponent
         */
        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.injectIntoApplication(this)
        // 3) create reminder work
        createAndConfigureWork()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return if (BuildConfig.DEBUG) {
            Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.DEBUG)
                .build()
        } else {
            Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.ERROR)
                .build()
        }
    }


    /**
     * Creates periodic work that reminds users to take a quiz one time per week.
     */
    private fun createAndConfigureWork() {
        // 1) create constraints
        val workManager = WorkManager.getInstance(this)

        val constraints = Constraints.Builder()
            .build()
        // 2) create WorkRequest
        val reminderWorkRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
            REMINDER_REPEAT_INTERVAL, TimeUnit.DAYS
        )
            .setConstraints(constraints)
            .build()
        // 3) Enqueue the work
        workManager.enqueueUniquePeriodicWork(
            UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            reminderWorkRequest
        )
    }
}

// TODO: 25.11.22 1) WorkManager advance configuration and testing