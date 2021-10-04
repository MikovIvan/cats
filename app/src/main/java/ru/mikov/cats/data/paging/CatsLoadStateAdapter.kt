package ru.mikov.cats.data.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.mikov.cats.databinding.LoadStateItemBinding
import ru.mikov.cats.ui.adapter.CatsAdapter

class CatsLoadStateAdapter(
    private val adapter: CatsAdapter
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LoadStateItemBinding.inflate(layoutInflater, parent, false)
        return LoadStateViewHolder(binding) { adapter.retry() }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)
}

class LoadStateViewHolder(
    private val binding: LoadStateItemBinding,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        with(binding) {
            btnRetry.setOnClickListener { retry() }
            progressBar.isVisible = loadState is LoadState.Loading
            btnRetry.isVisible = loadState is LoadState.Error
            tvError.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
            tvError.text = (loadState as? LoadState.Error)?.error?.message
        }
    }
}
