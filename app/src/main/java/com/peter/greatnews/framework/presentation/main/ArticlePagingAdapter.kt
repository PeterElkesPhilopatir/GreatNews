package com.peter.greatnews.framework.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.peter.greatnews.R
import com.peter.greatnews.business.model.Article
import com.peter.greatnews.databinding.RowArticleBinding


class ArticlePagingAdapter(val onClickListener: OnClickListener) :
    PagingDataAdapter<Article, ArticlesViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val binding = DataBindingUtil.inflate<RowArticleBinding>(
            LayoutInflater.from(parent.context), R.layout.row_article, parent, false
        )
        return ArticlesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
        holder.itemView.setOnClickListener {
            getItem(position)?.let { it1 -> onClickListener.onClick(it1) }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }
    }

}

class ArticlesViewHolder(private var binding: RowArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(Article: Article) {
        binding.data = Article
        binding.executePendingBindings()

    }
}

class OnClickListener(val clickListener: (Article: Article) -> Unit) {
    fun onClick(Article: Article) = clickListener(Article)
}