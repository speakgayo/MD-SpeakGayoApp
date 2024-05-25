package com.khairililmi.speakgayo.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khairililmi.speakgayo.databinding.HistoryItemBinding

class HistoryAdapter(private val dataList: List<HistoryDataModel>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HistoryItemBinding.inflate(layoutInflater, parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class HistoryViewHolder(private val binding: HistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: HistoryDataModel) {
            binding.inLang.text = data.inLang
            binding.inLangHistory.text = data.inLangHistory
            binding.gyLang.text = data.gyLang
            binding.gyLangHistory.text = data.gyLangHistory

            // Jika Anda ingin melakukan binding untuk ImageView atau elemen lainnya, lakukan di sini.
            // Misalnya:
            // binding.starImage.setImageResource(data.starImageResId)

            // Jika Anda memerlukan penanganan klik, tambahkan di sini.
            // binding.starImage.setOnClickListener { /* Handle click event */ }
        }
    }
}

