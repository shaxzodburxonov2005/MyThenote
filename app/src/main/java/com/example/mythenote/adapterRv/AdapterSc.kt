package com.example.mythenote.adapterRv

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mythenote.R
import com.example.mythenote.databinding.ItemInformationBinding
import com.example.mythenote.databinding.ItemRvBinding
import com.example.mythenote.model.InformationDB
import com.example.mythenote.model.Users

class AdapterSc(var context: Context, var itemOnclick: ItemOnClick) :
    androidx.recyclerview.widget.ListAdapter<InformationDB, AdapterSc.PostViewHolder>(
        DiffCallback
    ) {


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<InformationDB>() {
            override fun areItemsTheSame(oldItem: InformationDB, newItem: InformationDB): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: InformationDB,
                newItem: InformationDB
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemInformationBinding.inflate(
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
        var binding: ItemInformationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: InformationDB, position: Int) {
            binding.infoData.text = article.patientSheet
          //  binding.imgRv.setImageURI(Uri.parse(article.imag))
            binding.root.setOnClickListener {
                itemOnclick.nextData(article, position)
            }
        }
    }

    interface ItemOnClick {
        fun nextData(article: InformationDB, position: Int)

    }
}