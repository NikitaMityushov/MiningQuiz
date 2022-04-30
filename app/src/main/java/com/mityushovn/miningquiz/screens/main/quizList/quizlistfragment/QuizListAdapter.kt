package com.mityushovn.miningquiz.screens.main.quizList.quizlistfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mityushovn.miningquiz.screens.main.quizList.examsfragment.ExamsFragment
import com.mityushovn.miningquiz.screens.main.quizList.mistakesfragment.MistakesFragment
import com.mityushovn.miningquiz.screens.main.quizList.topicslistfragment.TopicsListFragment
import java.lang.AssertionError

class QuizListAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        // 3 items for TabLayout: Exams, Topics, Mistakes
        // TODO: 29.04.2022 mistakes mode
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ExamsFragment()
            1 -> TopicsListFragment()
//            2 -> MistakesFragment() TODO: 29.04.2022 mistakes mode
            else -> throw AssertionError("Must be 3 items")
        }
    }

}