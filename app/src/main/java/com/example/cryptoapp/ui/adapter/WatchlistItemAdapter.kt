package com.example.cryptoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.data.model.CryptoItem
import com.example.cryptoapp.databinding.FragmentWatchlistItemBinding
import java.util.Locale

/**
 * An adapter class for watchlist recyclerview items. Takes a list of [currencies] and a [deleteListener]
 * lambda as parameters.
 */
class WatchlistItemAdapter(
    private val currencies: List<CryptoItem>,
    private val deleteListener: (CryptoItem) -> Unit,
) : RecyclerView.Adapter<WatchlistItemAdapter.WatchlistItemViewHolder>() {

    inner class WatchlistItemViewHolder(val binding: FragmentWatchlistItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistItemViewHolder =
        WatchlistItemViewHolder(
            FragmentWatchlistItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))

    override fun getItemCount(): Int {
        return currencies.size
    }

    override fun onBindViewHolder(holder: WatchlistItemViewHolder, position: Int) {
        holder.binding.apply {
            percentage.text = String.format(Locale.getDefault(), "%s %%", currencies[position].percentage)
            cryptoName.text = currencies[position].name
            if (currencies[position].percentage < 0f) {
                upDownArrow.setImageResource(R.drawable.baseline_trending_down_24)
            } else if (currencies[position].percentage > 0f) {
                upDownArrow.setImageResource(R.drawable.baseline_trending_up_24)
            } else {
                upDownArrow.setImageResource(R.drawable.baseline_trending_flat_24)
            }

            listItem.setOnClickListener {
                deleteListener.invoke(currencies[position])
            }
        }
    }
}