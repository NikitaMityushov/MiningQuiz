package com.mityushovn.quizlist_feature.internal.presentation.mistakesfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mityushovn.quizlist_feature.R

internal class MistakesFragment : Fragment() {

    private lateinit var viewModel: MistakesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mistakes_fragment, container, false)
    }

}