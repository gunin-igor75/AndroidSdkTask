package com.github.gunin_igor75.androidsdktask

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private val permissionRequestLauncherWorker = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ::launchWorker
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionRequestLauncherWorker.launch(
                Manifest.permission.POST_NOTIFICATIONS
            )
        }
    }

    private fun launchWorker(isGranted: Boolean) {
        if (isGranted) {
            viewModel.setWorkManager(WorkManager.getInstance(applicationContext))
            viewModel.launchWorker()
        }
    }
}