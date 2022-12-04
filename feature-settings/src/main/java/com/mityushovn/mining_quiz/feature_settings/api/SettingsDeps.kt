package com.mityushovn.mining_quiz.feature_settings.api

import android.content.SharedPreferences
import com.mityushovn.miningquiz.module_injector.interfaces.Dependencies

interface SettingsDeps : Dependencies {
    val prefs: SharedPreferences
}


// TODO: 1.12.22 1) Fragment and ViewModel 2) create docs for non-UI feature creating. 3) create dagger graph and modules