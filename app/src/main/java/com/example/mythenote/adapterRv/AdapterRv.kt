package com.example.mythenote.adapterRv

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mythenote.databinding.ItemRvBinding
import com.example.mythenote.model.UserWithInfo
import com.example.mythenote.model.Users

class AdapterRv(var context: Context, var onItemClicked:Next) :
    androidx.recyclerview.widget.ListAdapter<Users, AdapterRv.PostViewHolder>(
        DiffCallback
    ) {


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Users>() {
            override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class PostViewHolder(
        var binding: ItemRvBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Users, position: Int) {
          binding.nameTv.text=article.firstName
            binding.surnameTv.text=article.lastName
            binding.nameTv.setOnClickListener {
                onItemClicked.nextData(article,position)
            }
            binding.nextImg.setOnClickListener {
                onItemClicked.save(article,position)
            }

        }
    }
    interface Next{
        fun nextData(article: Users , position: Int)
        fun save(article: Users,position: Int)
    }
}