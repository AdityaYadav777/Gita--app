package com.aditya.gita.View.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aditya.gita.databinding.ItemViewVersesBinding

class AdapterVerse(val onVerseItemVClicked: (String, Int) -> Unit) :RecyclerView.Adapter<AdapterVerse.myViewHolder>() {

    class myViewHolder(val binding:ItemViewVersesBinding):RecyclerView.ViewHolder(binding.root)

    val diffUtil= object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }

    }

    val differ=AsyncListDiffer(this,diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return myViewHolder(ItemViewVersesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val verse=differ.currentList[position]
        holder.binding.verseCount.text="Verse ${position+1}"
        holder.binding.verseDesc.text=verse

        holder.binding.myView.setOnClickListener {
            onVerseItemVClicked(verse,position+1)
        }

    }
}