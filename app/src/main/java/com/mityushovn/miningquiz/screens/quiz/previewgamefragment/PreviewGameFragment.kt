package com.mityushovn.miningquiz.screens.quiz.previewgamefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mityushovn.miningquiz.MiningQuizApplication
import com.mityushovn.miningquiz.di.Navigators
import com.mityushovn.miningquiz.activities.quiz.GameEngineFactory
import com.mityushovn.miningquiz.activities.quiz.GameEngine
import com.mityushovn.miningquiz.databinding.FragmentPreviewGameBinding
import com.mityushovn.miningquiz.navigation.QuizNavigator
import javax.inject.Inject

class PreviewGameFragment : Fragment() {
    private lateinit var binding: FragmentPreviewGameBinding
    private val navigator: QuizNavigator = Navigators.quizNavigator

    @Inject
    lateinit var vmFactory: GameEngineFactory
    private val quizActivityViewModel by activityViewModels<GameEngine> {
        vmFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().application as MiningQuizApplication).appComponent.injectInPreviewGameFragment(
            this
        )
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