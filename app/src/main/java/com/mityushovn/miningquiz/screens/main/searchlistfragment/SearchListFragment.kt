package com.mityushovn.miningquiz.screens.main.searchlistfragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mityushovn.miningquiz.R

class SearchListFragment : Fragment() {

    private lateinit var viewModel: SearchListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_list_fragment, container, false)
    }

}