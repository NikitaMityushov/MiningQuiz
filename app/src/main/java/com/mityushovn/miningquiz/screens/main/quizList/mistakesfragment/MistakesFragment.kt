package com.mityushovn.miningquiz.screens.main.quizList.mistakesfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mityushovn.miningquiz.R

class MistakesFragment : Fragment() {

    private lateinit var viewModel: MistakesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mistakes_fragment, container, false)
    }

}