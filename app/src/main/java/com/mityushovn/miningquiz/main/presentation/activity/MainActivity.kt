package com.mityushovn.miningquiz.main.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.MiningQuizApplication
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var vmFactory: MainActivityVMFactory
    private val mainActivityViewModel: MainActivityViewModel by viewModels {
        vmFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        (application as MiningQuizApplication).appComponent.injectInMainActivity(this)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        // disable
    }
}
