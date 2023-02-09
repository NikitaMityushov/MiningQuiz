package com.mityushovn.miningquiz.feature_game.internal.presentation.congratsfragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mityushovn.miningquiz.feature_game.api.QuizActivity
import com.mityushovn.miningquiz.feature_game.databinding.FragmentCongratsBinding
import com.mityushovn.miningquiz.feature_game.internal.navigation.QuizNavigator
import com.mityushovn.miningquiz.feature_game.internal.presentation.GameEngine
import com.mityushovn.miningquiz.feature_game.internal.presentation.GameEngineFactory
import javax.inject.Inject

internal class CongratsFragment : Fragment() {

    private lateinit var binding: FragmentCongratsBinding

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