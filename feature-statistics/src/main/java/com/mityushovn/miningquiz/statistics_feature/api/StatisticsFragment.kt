package com.mityushovn.miningquiz.statistics_feature.api

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mityushovn.miningquiz.module_injector.extensions.findDependencies
import com.mityushovn.miningquiz.statistics_feature.R
import com.mityushovn.miningquiz.statistics_feature.databinding.StatisticsFragmentBinding
import com.mityushovn.miningquiz.statistics_feature.internal.di.components.DaggerStatisticsComponent
import com.mityushovn.miningquiz.statistics_feature.internal.presentation.StatisticsVMFactory
import com.mityushovn.miningquiz.statistics_feature.internal.presentation.StatisticsViewModel
import com.mityushovn.miningquiz.utils.collectEventOnLifecycle
import com.mityushovn.miningquiz.utils.hideKeyboard
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
        DaggerStatisticsComponent
            .factory()
            .create(findDependencies(), requireActivity().application)
            .inject(this)

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

        statisticsViewModel.eventDeletedStats.collectEventOnLifecycle(viewLifecycleOwner) {
            showIfDeletedStatToast(it)
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

    private fun showIfDeletedStatToast(it: Boolean) {
        if (it) {
            Toast.makeText(
                requireContext(),
                getString(R.string.stat_deleted_successfuly),
                Toast.LENGTH_SHORT
            ).show()
            statisticsViewModel.updateStats() // if deleted successfully, you need to refresh prepared strings
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.stat_deleted_failed),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}
