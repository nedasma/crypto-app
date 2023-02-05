package com.example.cryptoapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentCryptoListBinding
import com.example.cryptoapp.ui.adapter.CryptoListItemAdapter
import com.example.cryptoapp.ui.viewmodel.MainViewModel
import com.example.cryptoapp.ui.viewmodel.PAGE_INCREMENTER
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * The fragment of the cryptocurrency list view - contains all methods and functionality related to
 * the display of the contents coming from the [viewModel] to the end user.
 */
class CryptoListFragment : Fragment() {

    private var _binding: FragmentCryptoListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<MainViewModel>()

    private var isScrolling = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCryptoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadData()

        val adapter = CryptoListItemAdapter { item ->
            viewModel.addToWatchlist(item)
            Snackbar.make(view, R.string.watchlist_item_added, Snackbar.LENGTH_SHORT).show()
        }
        binding.currenciesList.adapter = adapter
        binding.currenciesList.layoutManager = LinearLayoutManager(context)
        binding.currenciesList.addOnScrollListener(this@CryptoListFragment.scrollListener)

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.collectLatest {
                    adapter.differ.submitList(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // The scroll listener allows pagination of queries, so that once the end of the list is reached,
    // the VM is informed that a new query with pagination parameter needs to be requested and returned
    // back to the view
    private var scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            isScrolling = (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager

            val isLastItem = layoutManager.findFirstVisibleItemPosition() + layoutManager.childCount >= layoutManager.itemCount
            val isNotFirstItem = layoutManager.findFirstVisibleItemPosition() >= 0
            val isTotalMoreThanVisible = layoutManager.itemCount >= PAGE_INCREMENTER

            val paginate = isLastItem && isNotFirstItem && isTotalMoreThanVisible && isScrolling

            if (paginate) {
                viewModel.loadData(setIncrementer = true)
                isScrolling = false
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CryptoListFragment()
    }
}