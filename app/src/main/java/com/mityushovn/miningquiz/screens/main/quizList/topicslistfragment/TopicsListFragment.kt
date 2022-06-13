package com.mityushovn.miningquiz.screens.main.quizList.topicslistfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.miningquiz.MiningQuizApplication
import com.mityushovn.miningquiz.di.Navigators
import com.mityushovn.miningquiz.databinding.FragmentTopicsListBinding
import com.mityushovn.miningquiz.activities.main.MainActivity
import com.mityushovn.miningquiz.navigation.MainNavigator
import com.mityushovn.miningquiz.screens.recyclerview.adapters.TopicsListFrAdapter
import com.mityushovn.miningquiz.utils.hideKeyboard
import javax.inject.Inject

/**
 * @author Nikita Mityushov
 * @since 1.0
 * @property navigator is instance of a MainNavigation interface responsible for navigation
 * through screens(fragments) in the MainActivity.
 * @see MainNavigator
 */
class TopicsListFragment : Fragment() {
    @Inject
    lateinit var vmFactory: TopicsListVMFactory
    private val topicsListViewModel: TopicsListViewModel by viewModels {
        vmFactory
    }
    private val navigator: MainNavigator = Navigators.mainNavigator
    private lateinit var binding: FragmentTopicsListBinding
    private lateinit var recyclerView: RecyclerView
    private val listAdapter: TopicsListFrAdapter = TopicsListFrAdapter { topicId ->
        navigator.onTopicSelected(topicId, requireActivity() as MainActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (requireActivity().application as MiningQuizApplication).appComponent.injectInTopicsFragment(this)
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
            it.swapAdapter(listAdapter, true) // swapAdapter clears only cache, not a ViewPool!
//            it.setRecycledViewPool(viewPool) // set RecycledViewPool todo
//            it.recycledViewPool.setMaxRecycledViews(
//                R.layout.topic_item,
//                6
//            ) // max items in TopicListFragment RecycleView is six, default is 5
            it.setHasFixedSize(true) // changes in the adapter cannot affect to size of RecycleView, it's constant.
        }

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