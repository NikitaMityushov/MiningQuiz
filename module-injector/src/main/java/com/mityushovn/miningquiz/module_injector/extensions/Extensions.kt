package com.mityushovn.miningquiz.module_injector.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.mityushovn.miningquiz.module_injector.interfaces.Dependencies
import com.mityushovn.miningquiz.module_injector.interfaces.DependenciesProvider
import java.lang.IllegalStateException

/**
 * Map of dependencies for entities that contains dependencies
 */
typealias DepsMap = Map<Class<out Dependencies>, Dependencies>

/**
 * Get dependencies from parent(application or activity or fragment)
 */
inline fun <reified D : Dependencies> Fragment.findDependencies(): D {
    return parents.firstNotNullOfOrNull { it.depsMap[D::class.java] } as D?
        ?: throw IllegalStateException("Has no ${D::class.java} in $parents")
}

/**
 * @property parents is Iterable of parents that contains dependencies
 */
val Fragment.parents: Iterable<DependenciesProvider>
    get() = allParents.mapNotNull { it as? DependenciesProvider }

/**
 * Helper class for getting all parents of fragment
 */
private val Fragment.allParents: Iterable<Any>
    get() = object : Iterable<Any> {
        override fun iterator() = object : Iterator<Any> {
            private var currentParentFragment: Fragment? = parentFragment

            @SuppressLint("StaticFieldLeak")
            private var parentActivity: Activity? = activity
            private var parentApplication: Application? = parentActivity?.application

            override fun hasNext() =
                currentParentFragment != null || parentActivity != null || parentApplication != null

            override fun next(): Any {
                currentParentFragment?.let { parent ->
                    currentParentFragment = parent.parentFragment
                    return parent
                }

                parentActivity?.let { parent ->
                    parentActivity = null
                    return parent
                }

                parentApplication?.let { parent ->
                    parentApplication = null
                    return parent
                }

                throw NoSuchElementException()
            }
        }
    }