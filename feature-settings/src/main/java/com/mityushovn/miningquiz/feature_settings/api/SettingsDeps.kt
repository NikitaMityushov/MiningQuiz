package com.mityushovn.mining_quiz.feature_settings.api

import android.content.SharedPreferences
import com.mityushovn.miningquiz.module_injector.interfaces.Dependencies

interface SettingsDeps : Dependencies {
    val prefs: SharedPreferences
}

// TODO: 4.12.22  1) create docs for non-UI feature creating. 2) slider and switch improvements