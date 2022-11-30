package com.mityushovn.miningquiz.data_settings.api

import android.content.SharedPreferences
import com.mityushovn.miningquiz.core_domain.domain.repositories.SettingsRepositoryApi
import com.mityushovn.miningquiz.data_settings.internal.SettingsRepository

/**
 *  Represents a Factory that creates singleton of [SettingsRepositoryApi] implementation.
 *  @see SettingsRepositoryApi
 */
class SettingsRepositoryFactory {

    companion object {
        private var instance: SettingsRepositoryApi? = null

        fun createInstance(prefs: SharedPreferences): SettingsRepositoryApi {
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