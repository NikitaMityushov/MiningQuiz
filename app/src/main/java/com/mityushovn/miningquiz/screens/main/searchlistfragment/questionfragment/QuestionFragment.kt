package com.mityushovn.miningquiz.screens.main.searchlistfragment.questionfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mityushovn.miningquiz.MiningQuizApplication
import com.mityushovn.miningquiz.databinding.QuestionFragmentBinding
import com.mityushovn.miningquiz.utils.hideKeyboard
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Nikita Mityushov 19.04.22
 * @since 1.0
 */
class QuestionFragment : Fragment() {

    companion object {
        const val ARG_QUESTION_ID = "QUESTION_ID"
    }

    private lateinit var binding: QuestionFragmentBinding
    private var questionId: Int = 0

    @Inject
    lateinit var vmFactory: QuestionVMFactoryAssistedFactory
    private val questionViewModel: QuestionViewModel by viewModels {
        vmFactory.create(questionId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        questionId = arguments?.getInt(ARG_QUESTION_ID) as Int
        Timber.d("QuestionFragment questionId = $questionId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().application as MiningQuizApplication).appComponent.injectInQuestionFragment(this)
        binding = QuestionFragmentBinding.inflate(inflater, container, false)

        /*
            init data binding
         */
        with(binding) {
            viewModel = questionViewModel
            lifecycleOwner = this@QuestionFragment.viewLifecycleOwner
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