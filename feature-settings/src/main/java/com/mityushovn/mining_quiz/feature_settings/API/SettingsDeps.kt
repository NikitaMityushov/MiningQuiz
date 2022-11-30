package com.mityushovn.mining_quiz.feature_settings.API

import android.content.SharedPreferences
import com.mityushovn.miningquiz.module_injector.interfaces.Dependencies

interface SettingsDeps : Dependencies {
    val prefs: SharedPreferences
}


// TODO: 29.11.22 1) create dagger graph and modules
// TODO: 30.11.22 1) compose or View??  2) ViewModel 3) create docs for non-UI feature creating.