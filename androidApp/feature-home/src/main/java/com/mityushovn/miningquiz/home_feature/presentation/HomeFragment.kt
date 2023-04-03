package com.mityushovn.miningquiz.home_feature.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mityushovn.miningquiz.home_feature.R
import com.mityushovn.miningquiz.utils.hideKeyboard

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: 13.06.2022 add spannable text

        // hide keyboard when scrolls
        view.setOnScrollChangeListener { v, _, _, _, _ ->
            v.hideKeyboard()
        }
    }
}