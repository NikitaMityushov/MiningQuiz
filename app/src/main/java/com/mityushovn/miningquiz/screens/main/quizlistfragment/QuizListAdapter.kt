package com.mityushovn.miningquiz.screens.main.quizlistfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mityushovn.miningquiz.screens.main.examsfragment.ExamsFragment
import com.mityushovn.miningquiz.screens.main.mistakesfragment.MistakesFragment
import com.mityushovn.miningquiz.screens.main.topicslistfragment.TopicsListFragment
import java.lang.AssertionError

class QuizListAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        // 3 items for TabLayout: Exams, Topics, Mistakes
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ExamsFragment()
            1 -> TopicsListFragment()
            2 -> MistakesFragment()
            else -> throw AssertionError("Must be 3 items")
        }
    }

}