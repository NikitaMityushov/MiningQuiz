package com.mityushovn.miningquiz.main.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.common.MiningQuizApplication
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var vmFactory: MainActivityVMFactory
    private val mainActivityViewModel: MainActivityViewModel by viewModels {
        vmFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MiningQuizApplication).appComponent.injectInMainActivity(this)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        // disable
    }
}
