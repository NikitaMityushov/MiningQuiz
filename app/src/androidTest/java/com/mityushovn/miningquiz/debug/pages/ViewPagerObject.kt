package com.mityushovn.miningquiz.debug.pages

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.contrib.ViewPagerActions
import androidx.test.espresso.matcher.ViewMatchers

class ViewPagerObject(
    viewInteraction: ViewInteraction
) : ViewObject(viewInteraction) {

    companion object {
        fun viewPagerWithId(resId: Int, block: ViewPagerObject.() -> Unit) {
            ViewPagerObject(Espresso.onView(ViewMatchers.withId(resId))).apply(block)
        }
    }

    fun swipeLeft() {
        viewInteraction.perform(ViewPagerActions.scrollLeft())
    }

    fun swipeRight() {
        viewInteraction.perform(ViewPagerActions.scrollRight())
    }

    fun swipeToPage(page: Int) {
        viewInteraction.perform(ViewPagerActions.scrollToPage(page))
    }

    fun swipeToFirst() {
        viewInteraction.perform(ViewPagerActions.scrollToFirst())
    }

    fun swipeToLast() {
        viewInteraction.perform(ViewPagerActions.scrollToLast())
    }
}