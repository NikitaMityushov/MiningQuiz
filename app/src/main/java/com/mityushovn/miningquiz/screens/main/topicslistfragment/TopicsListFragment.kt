package com.mityushovn.miningquiz.screens.main.topicslistfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mityushovn.miningquiz.databinding.FragmentTopicsListBinding

class TopicsListFragment : Fragment() {

    private lateinit var binding: FragmentTopicsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTopicsListBinding.inflate(inflater, container, false)

        return binding.root
    }

}