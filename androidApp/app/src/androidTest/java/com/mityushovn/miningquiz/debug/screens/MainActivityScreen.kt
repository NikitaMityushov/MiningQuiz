package com.mityushovn.miningquiz.debug.screens

import android.view.View
import android.widget.SearchView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.core_testing.ui.pages.ViewObject.Companion.viewWithId
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

private const val homeId = R.id.home
private const val quizId = R.id.quizlist
private const val stats = R.id.statistics
private const val searchButton = R.id.searchFragment
private const val closeBtn = R.id.toolbar

class MainActivityScreen {

    companion object {
        private var instance: MainActivityScreen? = null

        private fun getMainActivityScreen(): MainActivityScreen {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = MainActivityScreen()
                    }
                }
            }
            return instance!!
        }

        fun mainActivityScreen(func: MainActivityScreen.() -> Unit) =
            getMainActivityScreen().apply(func)
    }

    fun clickHome() {
        viewWithId(homeId) {
            click()
        }
    }

    fun clickQuiz() {
        viewWithId(quizId) {
            click()
        }
    }

    fun clickStatistics() {
        viewWithId(stats) {
            click()
        }
    }

    fun clickSearch() {
        viewWithId(searchButton) {
            click()
        }
    }

    fun clickBackButton() {
        viewWithId(closeBtn) {
            pressBack()
        }
    }

    //TODO: doesn't work
    fun typeSearchQuery(text: String) {
        viewWithId(R.id.search_src_text) {
            click()
            forceTypeText(text)
        }
    }

    private fun forceTypeText(text: String): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "force type text"
            }

            override fun getConstraints(): Matcher<View> {
                return allOf(isEnabled())
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as? SearchView)?.setQuery(text, false)
                uiController?.loopMainThreadUntilIdle()
            }
        }
    }
}