package com.mityushovn.quizlist_feature.internal.di.modules

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.mityushovn.miningquiz.game_feature.api.GameMode
import com.mityushovn.miningquiz.game_feature.api.QuizActivity
import com.mityushovn.quizlist_feature.internal.navigation.Navigator
import dagger.Module
import dagger.Provides

private const val EXAM_OR_TOPIC_ID = "exam_or_topic_id"
private const val GAME_MODE = "game_mode"

@Module
internal object NavigationModule {

    @Provides
    fun provideNavigator(): Navigator {
        return object : Navigator {
            override fun onExamSelected(examId: Int, activity: FragmentActivity) {
                val intent = Intent(activity, QuizActivity::class.java)
                intent.apply {
                    putExtra(EXAM_OR_TOPIC_ID, examId)
                    putExtra(GAME_MODE, GameMode.EXAM)
                }
                activity.startActivity(intent)
            }

            override fun onTopicSelected(topicId: Int, activity: FragmentActivity) {
                val intent = Intent(activity, QuizActivity::class.java)
                intent.apply {
                    putExtra(EXAM_OR_TOPIC_ID, topicId)
                    putExtra(GAME_MODE, GameMode.TOPIC)
                }
                activity.startActivity(intent)
            }
        }
    }
}