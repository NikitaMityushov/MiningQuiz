package com.mityushovn.miningquiz.core_testing.ui.pages

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.mityushovn.miningquiz.core_testing.ui.matchers.ToastMatcher

class ToastObject(
    viewInteraction: ViewInteraction
) : ViewObject(viewInteraction) {
    companion object {

        fun toastWithText(text: String, block: ToastObject.() -> Unit) {
            ToastObject(Espresso.onView(ViewMatchers.withText(text))).apply(block)
        }
    }

    override fun checkIsDisplayed() {
        viewInteraction.inRoot(ToastMatcher())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}