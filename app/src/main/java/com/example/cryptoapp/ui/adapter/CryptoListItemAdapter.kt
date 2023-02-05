package com.example.cryptoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.data.model.CryptoItem
import com.example.cryptoapp.databinding.FragmentCryptoListItemBinding
import java.util.Locale

/**
 * An adapter class for the recyclerview items existing in the cryptocurrencies list. Takes a list of
 * an [updateListener] lambda as parameter to pass data from here to the fragment.
 */
class CryptoListItemAdapter(
    private val updateListener: (CryptoItem) -> Unit,
) : RecyclerView.Adapter<CryptoListItemAdapter.CryptoListItemViewHolder>() {

    inner class CryptoListItemViewHolder(val binding: FragmentCryptoListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoListItemViewHolder =
        CryptoListItemViewHolder(FragmentCryptoListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CryptoListItemViewHolder, position: Int) {
        holder.binding.apply {
            percentage.text = String.format(Locale.getDefault(), "%s %%", differ.currentList[position].percentage)
            cryptoName.text = differ.currentList[position].name
            if (differ.currentList[position].percentage < 0f) {
                upDownArrow.setImageResource(R.drawable.baseline_trending_down_24)
            } else if (differ.currentList[position].percentage > 0f) {
                upDownArrow.setImageResource(R.drawable.baseline_trending_up_24)
            } else {
                upDownArrow.setImageResource(R.drawable.baseline_trending_flat_24)
            }

            listItem.setOnClickListener {
                updateListener.invoke(differ.currentList[position])
            }
        }
    }

    private val diffUtilCallback = object : DiffUtil.ItemCallback<CryptoItem>() {
        override fun areItemsTheSame(oldItem: CryptoItem, newItem: CryptoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CryptoItem, newItem: CryptoItem): Boolean {
            return oldItem == newItem
        }
    }

    // Exposed to the outer world - adapter data can be updated with differ.submitList(items)
    val differ = AsyncListDiffer(this, diffUtilCallback)
}