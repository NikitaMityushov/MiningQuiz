package com.mityushovn.miningquiz.core_domain.domain.repositories

import com.mityushovn.miningquiz.core_domain.domain.models.GameSettings

/**
 *  @author Nikita Mityushov 29.11.22
 *  @since 1.0.4
 *  Represents API to provide settings of the application.
 */
interface SettingsRepositoryAPI {

    /**
     * Represents dark mode toggle
     */
    var isDarkMode: Boolean

    /**
     *
     */
    var gameSettings: GameSettings
}
