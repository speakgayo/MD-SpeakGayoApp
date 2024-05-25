import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khairililmi.speakgayo.data.local.favorite.FavoriteEntity
import com.khairililmi.speakgayo.databinding.FavoriteItemBinding

class FavoriteAdapter(private val onDeleteClickListener: (FavoriteEntity) -> Unit) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var favorites = emptyList<FavoriteEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    fun setData(favorites: List<FavoriteEntity>) {
        this.favorites = favorites
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val binding: FavoriteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.starImage.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val favorite = favorites[position]
                    onDeleteClickListener(favorite)
                    Log.d(FAVORITEADAPTER, "Item deleted: $favorite")
                }
            }
        }
        fun bind(favorite: FavoriteEntity) {
            binding.inLang.text = favorite.inLang
            binding.inLangFavorite.text = favorite.inLangFavorite
            binding.gyLang.text = favorite.gyLang
            binding.gyLangFavorite.text = favorite.gyLangFavorite
        }
    }

    companion object{
        const val FAVORITEADAPTER="favoriteadapter"
    }
}
