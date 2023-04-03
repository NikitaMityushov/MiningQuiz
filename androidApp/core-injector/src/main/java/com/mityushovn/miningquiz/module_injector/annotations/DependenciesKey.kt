package com.mityushovn.miningquiz.module_injector.annotations

import com.mityushovn.miningquiz.module_injector.interfaces.Dependencies
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
annotation class DependenciesKey(val value: KClass<out Dependencies>)
