package com.mityushovn.miningquiz.screens.quiz.congratsfragment

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
import com.mityushovn.miningquiz.databinding.FragmentCongratsBinding
import com.mityushovn.miningquiz.navigation.QuizNavigator

class CongratsFragment : Fragment() {

    private lateinit var binding: FragmentCongratsBinding
    private val quizActivityViewModel by activityViewModels<GameEngine> {
        GameEngineFactory(
            Repositories.questionsRepository,
            Repositories.attemptsRepository,
            requireActivity().application
        )
    }
    private val navigator: QuizNavigator = Navigators.quizNavigator


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCongratsBinding.inflate(inflater, container, false)
        // init data binding
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = quizActivityViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // when continue button is pressed
        binding.congratsFrContinueBtn.setOnClickListener {
            navigator.quitTestFromCongratsFr(this)
        }

        // when repeat button is pressed
        binding.congratsFrRepeatBtn.setOnClickListener {
            quizActivityViewModel.repeatGame()
            navigator.popStack(this)
        }
    }

}