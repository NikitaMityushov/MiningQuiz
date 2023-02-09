package com.mityushovn.miningquiz.feature_quizlist.internal.presentation.examsfragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mityushovn.miningquiz.feature_quizlist.databinding.ExamsFragmentBinding
import com.mityushovn.miningquiz.feature_quizlist.internal.navigation.Navigator
import com.mityushovn.miningquiz.feature_quizlist.internal.presentation.examsfragment.recyclerview.ExamsFrListAdapter
import javax.inject.Inject

/**
 * @author Nikita Mityushov
 * @since 1.0
 * @property navigator is instance of a MainNavigation interface responsible for navigation
 * through screens(fragments) in the MainActivity.
 * @see Navigator
 */
internal class ExamsFragment : Fragment() {
    @Inject
    lateinit var vmFactory: ExamsVMFactory
    private val examsViewModel: ExamsViewModel by viewModels {
        vmFactory
    }

    @Inject
    lateinit var navigator: Navigator

    private var _binding: ExamsFragmentBinding? = null
    private val binding get() = _binding!!

    private val listAdapter: ExamsFrListAdapter by lazy {
        ExamsFrListAdapter { examId ->
            navigator.onExamSelected(examId, requireActivity())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // configure DI
        (parentFragment as com.mityushovn.miningquiz.feature_quizlist.api.QuizListFragment).component.injectInExamsFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ExamsFragmentBinding.inflate(inflater, container, false)

        /*
         * If you use Data Binding, you must initialize all variables assigned in the XML file
         * in the fragment
         */
        with(binding) {
            viewModel = examsViewModel
            adapter = listAdapter
            lifecycleOwner = this@ExamsFragment.viewLifecycleOwner

            examFrRecyclerView.also {
                it.swapAdapter(listAdapter, true) // swapAdapter clears only cache, not a ViewPool!
//            it.setRecycledViewPool(viewPool) // set RecycledViewPool
                it.setHasFixedSize(true) // changes in the adapter cannot affect to size of RecycleView, it's constant.
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.examFrRecyclerView.swapAdapter(null, true)
        _binding = null
    }

}