package com.mityushovn.miningquiz.data_settings.api

import android.content.SharedPreferences
import com.mityushovn.miningquiz.core_domain.domain.repositories.SettingsRepositoryAPI
import com.mityushovn.miningquiz.data_settings.internal.SettingsRepository

/**
 *  Represents a Factory that creates singleton of [SettingsRepositoryAPI] implementation.
 *  @see SettingsRepositoryAPI
 */
class SettingsRepositoryFactory {

    companion object {
        private var instance: SettingsRepositoryAPI? = null

        fun createInstance(prefs: SharedPreferences): SettingsRepositoryAPI {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = SettingsRepository(
                            prefs = prefs
                        )
                    }
                }
            }
            return instance!!
        }

        fun destroyInstance() {
            instance = null
        }
    }
}