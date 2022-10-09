package com.mityushovn.miningquiz.module_injector.interfaces

import com.mityushovn.miningquiz.module_injector.extensions.DepsMap

/**
 * Interface for all classes that provides dependencies for other modules
 */
interface DependenciesProvider {
    val depsMap: DepsMap
}