package com.example.mythenote.adapterRv

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mythenote.databinding.ItemchekBinding
import com.example.mythenote.model.Teeth

class AdapterChek (private val teeth: List<Teeth>) :
    RecyclerView.Adapter<AdapterChek.RecyclerItemViewHolder>() {

    inner class RecyclerItemViewHolder(private val binding:ItemchekBinding ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(teeth: Teeth) {
            if (binding.checkBox.isChecked){

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            ItemchekBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int = teeth.size

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.onBind(teeth[position])
    }
}