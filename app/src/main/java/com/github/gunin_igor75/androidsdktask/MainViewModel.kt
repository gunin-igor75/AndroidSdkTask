package com.github.gunin_igor75.androidsdktask

import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class MainViewModel : ViewModel() {

    private lateinit var workManager: WorkManager

    private val constraints: Constraints = Constraints.Builder()
        .setRequiresCharging(true)
        .build()

    fun setWorkManager(workManager: WorkManager) {
        this.workManager = workManager
    }

    fun launchWorker() {
        val request = OneTimeWorkRequestBuilder<MyWorker>()
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniqueWork(
            MyWorker.WORKER_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    fun cancelWorker() {
        workManager.cancelUniqueWork(MyWorker.WORKER_NAME)
    }
}