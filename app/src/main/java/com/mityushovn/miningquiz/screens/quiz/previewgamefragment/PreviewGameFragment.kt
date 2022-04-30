package com.mityushovn.miningquiz.screens.quiz.previewgamefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mityushovn.miningquiz.DI.Navigators
import com.mityushovn.miningquiz.DI.Repositories
import com.mityushovn.miningquiz.activities.quiz.GameEngineFactory
import com.mityushovn.miningquiz.activities.quiz.GameEngine
import com.mityushovn.miningquiz.databinding.FragmentPreviewGameBinding
import com.mityushovn.miningquiz.navigation.QuizNavigator

class PreviewGameFragment : Fragment() {
    private lateinit var binding: FragmentPreviewGameBinding
    private val navigator: QuizNavigator = Navigators.quizNavigator
    private val quizActivityViewModel by activityViewModels<GameEngine> {
        GameEngineFactory(
            Repositories.questionsRepository,
            Repositories.attemptsRepository,
            requireActivity().application
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