package com.mityushovn.miningquiz.main.presentation.quizList.quizlistfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.databinding.FragmentQuizListBinding
import com.mityushovn.miningquiz.common.utils.hideKeyboard

class QuizListFragment : Fragment() {
    private lateinit var binding: FragmentQuizListBinding
    private lateinit var viewModel: QuizListViewModel
    private lateinit var pager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private val tabTitles = arrayOf(
        R.string.quiz,
        R.string.topics,
//        R.string.mistakes todo Mistakes Mode
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // init block
        binding = FragmentQuizListBinding.inflate(inflater, container, false)
        pager = binding.quizListFrViewpager
        tabLayout = binding.quizListFrTabLayout
        pager.adapter = QuizListAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.setText(tabTitles[position])
        }.attach()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // hide keyboard when scrolls
        view.setOnScrollChangeListener { v, _, _, _, _ ->
            v.hideKeyboard()
        }
    }
}

