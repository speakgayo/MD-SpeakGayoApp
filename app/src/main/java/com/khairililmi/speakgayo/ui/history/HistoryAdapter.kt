package com.khairililmi.speakgayo.ui.history

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khairililmi.speakgayo.R
import com.khairililmi.speakgayo.data.local.history.HistoryEntity
import com.khairililmi.speakgayo.databinding.HistoryItemBinding

class HistoryAdapter(private val onDeleteClickListener: (HistoryEntity) -> Unit,
                     private val onFavoriteClickListener: (HistoryEntity) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {


    private var history = emptyList<HistoryEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(history[position])
    }

    override fun getItemCount(): Int {
        return history.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setData(historites: List<HistoryEntity>) {
        this.history = historites
        notifyDataSetChanged()
    }

    inner class HistoryViewHolder(private val binding: HistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.starImage.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val history = history[position]
                    onDeleteClickListener(history)
                    Log.d(HISTORYADAPTER, "Item deleted: $history")
                }
            }
            binding.starFavorite.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val history = history[position]
                    onFavoriteClickListener(history)
                    val newFavoriteStatus = !history.isFavorite
                    setStarImage(newFavoriteStatus)
                }
            }
        }
        fun setStarImage(isFavorite: Boolean) {
            binding.starFavorite.setImageResource(
                if (isFavorite) R.drawable.baseline_star_24_full
                else R.drawable.star_blue
            )
        }

        fun bind(history: HistoryEntity) {
            binding.inLang.text = history.inLang
            binding.inLangHistory.text = history.inLangHistory
            binding.gyLang.text = history.gyLang
            binding.gyLangHistory.text = history.gyLangHistory
            setStarImage(history.isFavorite)

            // Jika Anda ing                    historyViewModel.saveFavoriteStatus(history.id, newFavoriteStatus)in melakukan binding untuk ImageView atau elemen lainnya, lakukan di sini.
            // Misalnya:
            // binding.starImage.setImageResource(data.starImageResId)

            // Jika Anda memerlukan penanganan klik, tambahkan di sini.
            // binding.starImage.setOnClickListener { /* Handle click event */ }
        }
    }
    companion object{
        const val HISTORYADAPTER="historyadapter"
    }
}

