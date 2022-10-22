package com.mityushovn.quizlist_feature.api

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mityushovn.miningquiz.module_injector.extensions.findDependencies
import com.mityushovn.miningquiz.utils.hideKeyboard
import com.mityushovn.quizlist_feature.R
import com.mityushovn.quizlist_feature.databinding.FragmentQuizListBinding
import com.mityushovn.quizlist_feature.internal.di.components.QuizlistComponent
import com.mityushovn.quizlist_feature.internal.di.components.DaggerQuizlistComponent
import com.mityushovn.quizlist_feature.internal.presentation.quizlistfragment.QuizListAdapter

class QuizListFragment : Fragment() {
    private lateinit var binding: FragmentQuizListBinding
    private lateinit var pager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private var _component: QuizlistComponent? = null
    internal val component get() = _component!!

    private val tabTitles = arrayOf(
        R.string.quiz,
        R.string.topics,
//        R.string.mistakes todo Mistakes Mode
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _component = DaggerQuizlistComponent.factory().create(findDependencies())
    }


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

    override fun onDetach() {
        super.onDetach()
        _component = null
    }
}

