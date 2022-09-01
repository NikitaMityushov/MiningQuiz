package com.mityushovn.miningquiz.main.presentation.searchlistfragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.miningquiz.common.MiningQuizApplication
import com.mityushovn.miningquiz.main.presentation.activity.MainActivityVMFactory
import com.mityushovn.miningquiz.databinding.SearchListFragmentBinding
import com.mityushovn.miningquiz.common.navigation.MainNavigator
import com.mityushovn.miningquiz.presentation.screens.recyclerview.adapters.QuestionsSearchFrAdapter
import com.mityushovn.miningquiz.common.utils.hideKeyboard
import com.mityushovn.miningquiz.main.presentation.activity.MainActivityViewModel
import com.mityushovn.miningquiz.main.presentation.mainfragment.MainFragment
import javax.inject.Inject

/**
 * @author Nikita Mityushov
 * @since 1.0
 * @property navigator is instance of a MainNavigation interface responsible for navigation
 * through screens(fragments) in the MainActivity.
 * @see MainNavigator
 * @property mainActivityViewModel is MainActivityViewModel class instance that shared among
 * MainFragment and SearchListFragment.
 * @see MainFragment
 */
class SearchListFragment : Fragment() {
    /*
        shared ViewModel with MainActivity and MainFragment
     */
    @Inject
    lateinit var vmFactory: MainActivityVMFactory
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels {
        vmFactory
    }

    @Inject
    lateinit var navigator: MainNavigator

    private lateinit var binding: SearchListFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: QuestionsSearchFrAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // configure DI
        (requireActivity().application as MiningQuizApplication).appComponent.injectInSearchListFragment(
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchListFragmentBinding.inflate(inflater, container, false)
        listAdapter = QuestionsSearchFrAdapter { questionId ->
            //    destination of navigation
            view?.let {
                navigator.onQuestionSelected(it, questionId)
            }
        }
        /*
            init data binding
         */
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainActivityViewModel
            adapter = listAdapter
        }
        /*
            setup RecyclerView
         */
        recyclerView = binding.searchListFrRecyclerView.also {
            setupRecyclerView(it, listAdapter)
        }

        return binding.root
    }

    /**
     * Setups RecycleView.
     * When RV is scrolling system hides a keyboard.
     */
    private fun setupRecyclerView(rv: RecyclerView, listAdapter: QuestionsSearchFrAdapter) {
//        rv.adapter = listAdapter
        rv.swapAdapter(listAdapter, true) // swapAdapter clears only cache, not a ViewPool!
        // hides keyboard when scrolling RecyclerView
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    recyclerView.hideKeyboard()
                }
            }
        })
    }
}