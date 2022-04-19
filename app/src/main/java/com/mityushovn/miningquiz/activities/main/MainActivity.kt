package com.mityushovn.miningquiz.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.DI.Repositories

class MainActivity : AppCompatActivity() {
    // essential to init because view model is shared with MainFragment and SearchListFragment
    private val mainActivityViewModel: MainActivityViewModel by viewModels {
        MainActivityVMFactory(Repositories.questionsRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}