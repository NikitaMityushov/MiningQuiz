package com.mityushovn.miningquiz.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.mityushovn.miningquiz.MiningQuizApplication
import com.mityushovn.miningquiz.R
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    // essential to init because view model is shared with MainFragment and SearchListFragment
    @Inject
    lateinit var vmFactory: MainActivityVMFactory
    private val mainActivityViewModel: MainActivityViewModel by viewModels {
        vmFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.application as MiningQuizApplication).appComponent.injectInMainActivity(this)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        // disable
    }
}
