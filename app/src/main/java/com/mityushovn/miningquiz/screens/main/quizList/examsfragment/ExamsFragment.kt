package com.mityushovn.miningquiz.screens.main.quizList.examsfragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.miningquiz.MiningQuizApplication
import com.mityushovn.miningquiz.databinding.ExamsFragmentBinding
import com.mityushovn.miningquiz.activities.main.MainActivity
import com.mityushovn.miningquiz.navigation.MainNavigator
import com.mityushovn.miningquiz.screens.recyclerview.adapters.ExamsFrListAdapter
import javax.inject.Inject

/**
 * @author Nikita Mityushov
 * @since 1.0
 * @property navigator is instance of a MainNavigation interface responsible for navigation
 * through screens(fragments) in the MainActivity.
 * @see MainNavigator
 */
class ExamsFragment : Fragment() {
    @Inject
    lateinit var vmFactory: ExamsVMFactory
    private val examsViewModel: ExamsViewModel by viewModels {
        vmFactory
    }

    @Inject
    lateinit var navigator: MainNavigator

    private lateinit var binding: ExamsFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private val listAdapter: ExamsFrListAdapter by lazy {
        ExamsFrListAdapter { examId ->
            navigator.onExamSelected(examId, requireActivity() as MainActivity)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // configure DI
        (requireActivity().application as MiningQuizApplication).appComponent.mainComponent().injectInExamsFragment(
            this
        )
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
            it.swapAdapter(listAdapter, true) // swapAdapter clears only cache, not a ViewPool!
//            it.setRecycledViewPool(viewPool) // set RecycledViewPool
            it.setHasFixedSize(true) // changes in the adapter cannot affect to size of RecycleView, it's constant.
        }

        return binding.root
    }

}