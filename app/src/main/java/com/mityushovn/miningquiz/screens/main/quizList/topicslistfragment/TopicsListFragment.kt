package com.mityushovn.miningquiz.screens.main.quizList.topicslistfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.miningquiz.DI.Navigators
import com.mityushovn.miningquiz.databinding.FragmentTopicsListBinding
import com.mityushovn.miningquiz.DI.Repositories
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.navigation.MainNavigator
import com.mityushovn.miningquiz.screens.recyclerview.adapters.TopicsListFrAdapter

/**
 * @author Nikita Mityushov
 * @since 1.0
 * @property navigator is instance of a MainNavigation interface responsible for navigation
 * through screens(fragments) in the MainActivity.
 * @see MainNavigator
 */
class TopicsListFragment : Fragment() {

    private val topicsListViewModel: TopicsListViewModel by viewModels {
        TopicsListVMFactory(Repositories.topicsRepository)
    }
    private val navigator: MainNavigator = Navigators.mainNavigator
    private lateinit var binding: FragmentTopicsListBinding
    private lateinit var recyclerView: RecyclerView
    private val listAdapter: TopicsListFrAdapter = TopicsListFrAdapter { topicId ->
        navigator.onTopicSelected(topicId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopicsListBinding.inflate(inflater, container, false)
        /*
         * init data binding
         * If you're using Data Binding, you must initialize all variables assigned in the XML file
         * in the fragment
         */
        with(binding) {
            viewModel = topicsListViewModel
            adapter = listAdapter
            lifecycleOwner = this@TopicsListFragment.viewLifecycleOwner
        }

        recyclerView = binding.topicsListFrRecyclerView.also {
//            it.adapter = listAdapter
            it.swapAdapter(listAdapter, true) // swapAdapter clears only cache, not a ViewPool!
            it.recycledViewPool.setMaxRecycledViews(
                R.layout.topic_item,
                6
            ) // max items in TopicListFragment RecycleView is six, default is 5
        }

        return binding.root
    }

}