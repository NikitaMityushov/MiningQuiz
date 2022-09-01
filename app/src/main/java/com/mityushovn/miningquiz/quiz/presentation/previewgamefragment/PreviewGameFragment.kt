package com.mityushovn.miningquiz.quiz.presentation.previewgamefragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mityushovn.miningquiz.common.MiningQuizApplication
import com.mityushovn.miningquiz.quiz.presentation.quiz.GameEngineFactory
import com.mityushovn.miningquiz.quiz.presentation.quiz.GameEngine
import com.mityushovn.miningquiz.databinding.FragmentPreviewGameBinding
import com.mityushovn.miningquiz.common.navigation.QuizNavigator
import javax.inject.Inject

class PreviewGameFragment : Fragment() {
    private lateinit var binding: FragmentPreviewGameBinding

    @Inject
    lateinit var vmFactory: GameEngineFactory
    private val quizActivityViewModel by activityViewModels<GameEngine> {
        vmFactory
    }

    @Inject
    lateinit var navigator: QuizNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MiningQuizApplication).appComponent.injectInPreviewGameFragment(
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreviewGameBinding.inflate(inflater, container, false)
        // init data binding
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = quizActivityViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.previewFrStartBtn.setOnClickListener {
            navigator.toGameFragment(this, null)
        }
    }
}