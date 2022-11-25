package com.mityushovn.miningquiz.work

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.*
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.testing.WorkManagerTestInitHelper
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class ReminderWorkerTest {

    companion object {
        const val REMINDER_REPEAT_INTERVAL = 7L
        const val UNIQUE_WORK_NAME = "REMINDER TO PLAY"
    }

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun whenWorkerDoWorkCalledShouldReturnSuccess() {
        // given
        val worker = TestListenableWorkerBuilder<ReminderWorker>(context).build()
        runBlocking {
            // when
            val result = worker.doWork()
            // then
            assertEquals(ListenableWorker.Result.success(), result)
        }
    }

    @Test
    fun testReminderPeriodicWork() {
        // given
        val request =
            PeriodicWorkRequestBuilder<ReminderWorker>(REMINDER_REPEAT_INTERVAL, TimeUnit.DAYS)
                .build()

        WorkManagerTestInitHelper.initializeTestWorkManager(context) // !!! ты должен инициализировать тестовый WorkManager, иначе ClassCastException в WorkManagerTestInitHelper.getTestDriver(context)!!
        val workManager = WorkManager.getInstance(context)
        val testDriver = WorkManagerTestInitHelper.getTestDriver(context)!!
        // when
        workManager.enqueueUniquePeriodicWork(
            UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        ).result.get()

        with(testDriver) {
            setPeriodDelayMet(request.id)
            setAllConstraintsMet(request.id)
        }

        val workInfo = workManager.getWorkInfoById(request.id).get()

        // then
        assertEquals(WorkInfo.State.RUNNING, workInfo.state)
    }
}