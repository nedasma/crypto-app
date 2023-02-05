package com.example.cryptoapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentWatchlistBinding
import com.example.cryptoapp.ui.adapter.WatchlistItemAdapter
import com.example.cryptoapp.ui.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * The fragment of the watchlist view - contains all methods and functionality related to
 * the display of the contents coming from the [viewModel] to the end user.
 */
class WatchlistFragment : Fragment() {

    private var _binding: FragmentWatchlistBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadWatchlist()

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.watchlistData.collect {
                    val adapter = WatchlistItemAdapter(it) { item ->
                        viewModel.deleteFromWatchlist(item)
                        Snackbar.make(view, R.string.watchlist_item_deleted, Snackbar.LENGTH_SHORT).show()
                    }
                    binding.watchlist.adapter = adapter
                    binding.watchlist.layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadWatchlist()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = WatchlistFragment()
    }
}