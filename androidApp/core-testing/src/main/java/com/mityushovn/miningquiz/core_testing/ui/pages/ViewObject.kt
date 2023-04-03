package com.mityushovn.miningquiz.core_testing.ui.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId

open class ViewObject(
    protected val viewInteraction: ViewInteraction
) {

    companion object {
        fun viewWithId(resId: Int, block: ViewObject.() -> Unit) {
            ViewObject(onView(withId(resId))).apply(block)
        }
    }

    open fun click() {
        closeSoftKeyboard()
        viewInteraction.perform(ViewActions.click())
    }

    open fun scrollTo() {
        viewInteraction.perform(ViewActions.scrollTo())
    }

    open fun checkIsDisplayed() {
        viewInteraction.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}