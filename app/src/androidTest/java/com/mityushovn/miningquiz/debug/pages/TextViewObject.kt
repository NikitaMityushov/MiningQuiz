package com.mityushovn.miningquiz.debug.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText

class TextViewObject(
    viewInteraction: ViewInteraction
) : ViewObject(viewInteraction) {
    companion object {
        fun textViewWithId(resId: Int, block: TextViewObject.() -> Unit) {
            TextViewObject(onView(withId(resId))).apply(block)
        }

        fun textViewWithText(text: String, block: TextViewObject.() -> Unit) {
            TextViewObject(onView(withText(text))).apply(block)
        }
    }

    fun isTextSame(text: String) {
        viewInteraction.check(matches(withText(text)))
    }
}
