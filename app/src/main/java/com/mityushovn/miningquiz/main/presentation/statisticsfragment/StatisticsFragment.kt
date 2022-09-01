package com.mityushovn.miningquiz.main.presentation.statisticsfragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mityushovn.miningquiz.common.MiningQuizApplication
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.databinding.StatisticsFragmentBinding
import com.mityushovn.miningquiz.common.utils.hideKeyboard
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Nikita Mityushov
 * @since 1.0
 */
class StatisticsFragment : Fragment() {
    private lateinit var binding: StatisticsFragmentBinding

    @Inject
    lateinit var vmFactory: StatisticsVMFactory
    private val statisticsViewModel: StatisticsViewModel by viewModels {
        vmFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // configure DI
        (requireActivity().application as MiningQuizApplication).appComponent.injectInStatisticsFragment(
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StatisticsFragmentBinding.inflate(inflater, container, false)

        // init data binding
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = statisticsViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // hide keyboard when scrolls
        view.setOnScrollChangeListener { v, _, _, _, _ ->
            v.hideKeyboard()
        }

        binding.statFrResetStatisticsBtn.setOnClickListener {
            Timber.d("statFrResetStatisticsBtn onClickListener")
            showDeleteStatDialog()
        }
    }

    /**
     * Shows the alert dialog if "Quit test" button was pressed.
     * @see AlertDialog
     */
    private fun showDeleteStatDialog() {
        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> statisticsViewModel.deleteAllStatistics()
                DialogInterface.BUTTON_NEGATIVE -> Timber.d("Cancel quit dialog")
            }
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setIcon(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_baseline_sd_card_alert_24,
                    null
                )
            )
            .setTitle(getString(R.string.are_you_sure_want_delete_stat))
            .setPositiveButton(getString(R.string.yes), listener)
            .setNegativeButton(getString(R.string.no), listener)
            .create()

        dialog.show()
    }

}