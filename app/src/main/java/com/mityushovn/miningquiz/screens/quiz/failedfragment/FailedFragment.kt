package com.mityushovn.miningquiz.screens.quiz.failedfragment

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
import com.mityushovn.miningquiz.databinding.FragmentFailedBinding
import com.mityushovn.miningquiz.navigation.QuizNavigator

class FailedFragment : Fragment() {

    private lateinit var binding: FragmentFailedBinding
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
        binding = FragmentFailedBinding.inflate(inflater, container, false)
        // init data binding
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = quizActivityViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // when pressed "Continue: button
        binding.failedFrContinueBtn.setOnClickListener {
            navigator.quitTestFromFailedFr(this)
        }
        // when pressed "Repeat" button
        binding.failedFrRepeatBtn.setOnClickListener {
            quizActivityViewModel.repeatGame()
            navigator.popStack(this)
        }
    }

}