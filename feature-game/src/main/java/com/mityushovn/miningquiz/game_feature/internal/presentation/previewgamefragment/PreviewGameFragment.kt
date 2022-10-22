package com.mityushovn.miningquiz.game_feature.internal.presentation.previewgamefragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mityushovn.miningquiz.game_feature.api.QuizActivity
import com.mityushovn.miningquiz.game_feature.databinding.FragmentPreviewGameBinding
import com.mityushovn.miningquiz.game_feature.internal.navigation.QuizNavigator
import com.mityushovn.miningquiz.game_feature.internal.presentation.GameEngine
import com.mityushovn.miningquiz.game_feature.internal.presentation.GameEngineFactory
import javax.inject.Inject

internal class PreviewGameFragment : Fragment() {
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
        (requireActivity() as QuizActivity).component.inject(this)
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