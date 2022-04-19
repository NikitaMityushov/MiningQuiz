package com.mityushovn.miningquiz.screens.main.quizList.examsfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.miningquiz.DI.Navigators
import com.mityushovn.miningquiz.databinding.ExamsFragmentBinding
import com.mityushovn.miningquiz.DI.Repositories
import com.mityushovn.miningquiz.navigation.MainNavigator
import com.mityushovn.miningquiz.screens.recyclerview.adapters.ExamsFrListAdapter

/**
 * @author Nikita Mityushov
 * @since 1.0
 * @property navigator is instance of a MainNavigation interface responsible for navigation
 * through screens(fragments) in the MainActivity.
 * @see MainNavigator
 */
class ExamsFragment : Fragment() {

    private val examsViewModel: ExamsViewModel by viewModels {
        ExamsVMFactory(Repositories.examsRepository)
    }
    private val navigator: MainNavigator = Navigators.mainNavigator
    private lateinit var binding: ExamsFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private val listAdapter: ExamsFrListAdapter by lazy {
        ExamsFrListAdapter { examId ->
            navigator.onExamSelected(examId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExamsFragmentBinding.inflate(inflater, container, false)
        /*
         * If you use Data Binding, you must initialize all variables assigned in the XML file
         * in the fragment
         */
        with(binding) {
            viewModel = examsViewModel
            adapter = listAdapter
            lifecycleOwner = this@ExamsFragment.viewLifecycleOwner
        }

        recyclerView = binding.examFrRecyclerView.also {
//            it.adapter = listAdapter
            it.swapAdapter(listAdapter, true) // swapAdapter clears only cache, not a ViewPool!
        }

        return binding.root
    }

}