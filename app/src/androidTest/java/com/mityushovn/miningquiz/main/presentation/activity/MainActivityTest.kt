package com.mityushovn.miningquiz.main.presentation.activity

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mityushovn.miningquiz.debug.screens.ExamsFragmentScreen.Companion.examsFragmentScreen
import com.mityushovn.miningquiz.debug.screens.FailedFragmentScreen.Companion.failedFragmentScreen
import com.mityushovn.miningquiz.debug.screens.GameFragmentScreen.Companion.gameFragmentScreen
import com.mityushovn.miningquiz.debug.screens.MainActivityScreen.Companion.mainActivityScreen
import com.mityushovn.miningquiz.debug.screens.PreviewFragmentScreen.Companion.quizPreviewFragmentScreen
import com.mityushovn.miningquiz.debug.screens.QuizListFragmentScreen.Companion.quizListFragmentScreen
import com.mityushovn.miningquiz.debug.screens.StatisticsFragmentScreen.Companion.statisticsFragmentScreen

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun endToEndQuizFunctionalityTest() {
        mainActivityScreen {
            clickQuiz()
        }

        quizListFragmentScreen {
            clickExamsTab()
        }

        examsFragmentScreen {
            clickExamsRecycleViewOnPosition(2)
        }

        quizPreviewFragmentScreen {
            clickLetsStartBtn()
        }

        repeat(5) {
            gameFragmentScreen {
                repeat(10) {
                    clickPostponeBtn()
                }

                clickExitGameBtn()
                clickExitDialogNo()

                repeat(10) {
                    clickFirstAnswer()
                    clickContinueBtn()
                }

//                checkToastIsDisplayed()
            }

            failedFragmentScreen {
                clickRepeatBtn()
            }
        }

        gameFragmentScreen {
            repeat(10) {
                clickPostponeBtn()
            }

            clickExitGameBtn()
            clickExitDialogNo()

            repeat(10) {
                clickFirstAnswer()
                clickContinueBtn()
            }

//            checkToastIsDisplayed()
        }

        failedFragmentScreen {
            clickContinueBtn()
        }

        mainActivityScreen {
            clickSearch()
            clickBackButton()
            clickHome()
            clickStatistics()
        }

        statisticsFragmentScreen {
            clickResetStatisticsBtn()
            clickResetDialogYes()
        }

        mainActivityScreen {
            clickHome()
        }
    }

    @Test
    fun testStatisticsScreen() {
        mainActivityScreen {
            clickStatistics()
        }
        statisticsFragmentScreen {
            clickResetStatisticsBtn()
            clickResetDialogYes()
//            checkToastIsDisplayed()
        }
        mainActivityScreen {
            clickHome()
        }
    }

    @Test
    fun test_search_functionality() {
        mainActivityScreen {
            clickSearch()
            typeSearchQuery("test111111")
            clickBackButton()
        }
    }

}