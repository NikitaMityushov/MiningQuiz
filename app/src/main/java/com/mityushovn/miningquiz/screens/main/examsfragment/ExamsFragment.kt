package com.mityushovn.miningquiz.screens.main.examsfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.miningquiz.databinding.ExamsFragmentBinding
import com.mityushovn.miningquiz.models.Exam
import timber.log.Timber

class ExamsFragment : Fragment() {

    private val viewModel: ExamsViewModel by lazy {
        ViewModelProvider(this)[ExamsViewModel::class.java]
    }
    private lateinit var binding: ExamsFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: ExamsFrListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExamsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        listAdapter = ExamsFrListAdapter()
        recyclerView = binding.examFrRecyclerView.also {
            it.adapter = listAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.examsList.observe(viewLifecycleOwner, {
            updateUI(it)
        })
    }

    private fun updateUI(list: List<Exam>?) {
        list?.forEach {
            Timber.d(it.nameExam)
        }
        if (list != null) {
            listAdapter.list = list
        }
    }


}