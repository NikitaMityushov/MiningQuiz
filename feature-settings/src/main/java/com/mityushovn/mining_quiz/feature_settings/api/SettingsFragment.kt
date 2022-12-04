package com.mityushovn.mining_quiz.feature_settings.api

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mityushovn.mining_quiz.feature_settings.databinding.FragmentSettingsBinding
import com.mityushovn.mining_quiz.feature_settings.internal.di.components.DaggerSettingsComponent
import com.mityushovn.mining_quiz.feature_settings.internal.presentation.SettingsVMFactory
import com.mityushovn.mining_quiz.feature_settings.internal.presentation.SettingsViewModel
import com.mityushovn.miningquiz.module_injector.extensions.findDependencies
import javax.inject.Inject

private const val TAG = "SettingsFragment"

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    @Inject
    internal lateinit var factory: SettingsVMFactory
    private val viewModel: SettingsViewModel by viewModels {
        factory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // configure DI
        DaggerSettingsComponent
            .factory()
            .create(findDependencies())
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            settingsScreenSliderExams.addOnChangeListener { _, value, _ ->
                Log.d(TAG, "exam value is $value")
                viewModel.setExamNumberQuestions(value.toInt())
            }

            settingsScreenSliderTopics.addOnChangeListener { _, value, _ ->
                Log.d(TAG, "topic value is $value")
                viewModel.setTopicNumberQuestions(value.toInt())
            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}