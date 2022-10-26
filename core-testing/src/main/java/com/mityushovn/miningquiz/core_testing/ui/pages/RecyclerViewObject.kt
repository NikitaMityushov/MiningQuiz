package com.mityushovn.miningquiz.core_testing.ui.pages

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers

class RecyclerViewObject(
    viewInteraction: ViewInteraction
) : ViewObject(viewInteraction) {
    companion object {
        fun recycleViewWithId(resId: Int, block: RecyclerViewObject.() -> Unit) {
            RecyclerViewObject(Espresso.onView(ViewMatchers.withId(resId))).apply(block)
        }
    }

    fun scrollToPosition(position: Int) {
        viewInteraction.perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position)
        )
    }

    fun clickAtPosition(position: Int) {
        viewInteraction.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                ViewActions.click()
            )
        )
    }
}