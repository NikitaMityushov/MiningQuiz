package com.mityushovn.miningquiz.main.presentation.mainfragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.MiningQuizApplication
import com.mityushovn.miningquiz.main.presentation.activity.MainActivityVMFactory
import com.mityushovn.miningquiz.databinding.MainFragmentBinding
import com.mityushovn.miningquiz.navigation.MainNavigator
import com.mityushovn.miningquiz.main.presentation.searchlistfragment.SearchListFragment
import com.mityushovn.miningquiz.main.presentation.activity.MainActivityViewModel
import com.mityushovn.miningquiz.module_injector.extensions.DepsMap
import com.mityushovn.miningquiz.module_injector.interfaces.DependenciesProvider
import com.mityushovn.miningquiz.utils.onQueryTextChange
import com.mityushovn.miningquiz.utils.toGone
import com.mityushovn.miningquiz.utils.toVisible
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Nikita Mityushov
 * @since 1.0
 * @property navigator is instance of a MainNavigation interface responsible for navigation
 * through screens(fragments) in the MainActivity.
 * @see MainNavigator
 * @property mainActivityViewModel is MainActivityViewModel class instance that shared among
 * MainFragment and SearchListFragment.
 * @see SearchListFragment
 * MainFragment is a host fragment for HomeFragment, QuizListFragment, StatisticsFragment and
 * SearchListFragment.
 * @see NavHostFragment
 * @see R.id.tab_navigation
 */
class MainFragment : Fragment(), DependenciesProvider {
    private lateinit var binding: MainFragmentBinding
    private lateinit var controller: NavController

    @Inject
    override lateinit var depsMap: DepsMap

    /*
        shared ViewModel with MainActivity and SearchListFragment
     */
    @Inject
    lateinit var vmFactory: MainActivityVMFactory
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels {
        vmFactory
    }

    @Inject
    lateinit var navigator: MainNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // configure DI
        (requireActivity().application as MiningQuizApplication).appComponent.injectInMainFragment(
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        /*
            1) init menu
         */
        setHasOptionsMenu(true) // !!
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        /*
            2) init data binding
        */
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
            1) setup bottom navigation
         */
        val host: NavHostFragment =
            childFragmentManager.findFragmentById(R.id.host_fragment_container) as NavHostFragment
        controller = host.navController
        setupBottomNavMenu(controller)
    }

    /**
     * private method for setup bottom navigation view
     */
    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = binding.bottomNavView
        bottomNav.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        // 1) init and setup search view
        val searchView = menu.findItem(R.id.searchFragment).actionView as SearchView
        setupSearchView(searchView)

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.searchFragment) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * private method for setup SearchView on a Toolbar.
     */
    private fun setupSearchView(searchView: SearchView) {
        // 1) assign hint for EditText
        searchView.queryHint = getString(R.string.enter_the_question)
        searchView.setIconifiedByDefault(true) // не закрывать меню

        // 2) if search view is focused - open SearchListFragment
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                Timber.d("is Focused == true")
                binding.bottomNavView.toGone() // скрыть BottomNavBar когда в фокусе SearchView
                navigator.onSearchViewIsFocused(this)
            } else {
                Timber.d("is Focused == false")
                binding.toolbar.collapseActionView()
                binding.bottomNavView.toVisible() // вернуть BottomNavBar когда SearchView не в фокусе

                childFragmentManager.fragments[0]?.findNavController()
                    ?.popBackStack() // TODO: 01.06.2022 доработать
            }
        }

        // 3) handle text query
        searchView.onQueryTextChange {
            it?.let {
                mainActivityViewModel.handleInput(it)

                navigator.onSearchViewIsFocused(this) // TODO: 01.06.2022 доработать
            }
        }
    }

}