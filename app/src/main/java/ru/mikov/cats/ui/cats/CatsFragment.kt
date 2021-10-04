package ru.mikov.cats.ui.cats

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import ru.mikov.cats.R
import ru.mikov.cats.data.paging.CatsLoadStateAdapter
import ru.mikov.cats.databinding.FragmentCatsBinding
import ru.mikov.cats.ui.adapter.CatsAdapter

class CatsFragment : Fragment(R.layout.fragment_cats) {

    private val catsViewModel: CatsViewModel by viewModels()
    private val viewBinding: FragmentCatsBinding by viewBinding()
    private val catsAdapter = CatsAdapter { cat, view ->
        val extras = FragmentNavigatorExtras(
            view to cat.url
        )
        val action = CatsFragmentDirections.actionCatsFragmentToCatFragment(cat.url)
        findNavController().navigate(action, extras)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {
            with(catsAdapter) {
                swipeRefresh.setOnRefreshListener { catsAdapter.refresh() }
                rvCats.adapter = withLoadStateHeaderAndFooter(
                    header = CatsLoadStateAdapter(catsAdapter),
                    footer = CatsLoadStateAdapter(catsAdapter)
                )

                postponeEnterTransition()
                rvCats.viewTreeObserver.addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }

                with(catsViewModel) {
                    lifecycleScope.launch {
                        fetchCats().collectLatest { catsAdapter.submitData(it) }

                        loadStateFlow.collectLatest {
                            swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                        }

                        loadStateFlow.distinctUntilChangedBy { it.refresh }
                            .filter { it.refresh is LoadState.NotLoading }
                            .collect { rvCats.scrollToPosition(0) }
                    }
                }
            }
        }
    }
}
