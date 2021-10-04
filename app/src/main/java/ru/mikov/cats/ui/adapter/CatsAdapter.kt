package ru.mikov.cats.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.mikov.cats.data.models.Cat
import ru.mikov.cats.databinding.CatItemBinding
import ru.mikov.cats.ui.extensions.load

class CatsAdapter(
    private val listener: (Cat, image: ImageView) -> Unit
) :
    PagingDataAdapter<Cat, CatViewHolder>(CatComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CatItemBinding.inflate(layoutInflater, parent, false)
        return CatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!, listener)
    }
}

class CatViewHolder(
    private val binding: CatItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        cat: Cat,
        listener: (Cat, image: ImageView) -> Unit
    ) {
        with(binding.ivCat) {
            load(cat.url) {
                listener(cat, this)
            }
            transitionName = cat.url
        }
    }
}

object CatComparator : DiffUtil.ItemCallback<Cat>() {
    override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
        return oldItem == newItem
    }
}
