package com.mityushovn.miningquiz.game_feature.internal.presentation.failedfragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mityushovn.miningquiz.game_feature.api.QuizActivity
import com.mityushovn.miningquiz.game_feature.databinding.FragmentFailedBinding
import com.mityushovn.miningquiz.game_feature.internal.navigation.QuizNavigator
import com.mityushovn.miningquiz.game_feature.internal.presentation.GameEngine
import com.mityushovn.miningquiz.game_feature.internal.presentation.GameEngineFactory
import javax.inject.Inject

internal class FailedFragment : Fragment() {

    private lateinit var binding: FragmentFailedBinding

    @Inject
    lateinit var vmFactory: GameEngineFactory
    private val quizActivityViewModel by activityViewModels<GameEngine> {
        vmFactory
    }

    @Inject
    lateinit var navigator: QuizNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // configure DI
        (requireActivity() as QuizActivity).component.inject(this)
    }

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