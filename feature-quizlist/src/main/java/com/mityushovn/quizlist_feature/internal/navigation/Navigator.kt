package com.mityushovn.quizlist_feature.internal.navigation

import androidx.fragment.app.FragmentActivity

internal interface Navigator {
    fun onExamSelected(examId: Int, activity: FragmentActivity)
    fun onTopicSelected(topicId: Int, activity: FragmentActivity)
}