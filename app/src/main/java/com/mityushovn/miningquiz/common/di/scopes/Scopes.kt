package com.mityushovn.miningquiz.common.di.scopes

import javax.inject.Scope

/**
 * Dagger scope for dependency with lifetime equals lifetime of application
 */
@Scope
annotation class AppScope

@Scope
annotation class MainActivityScope