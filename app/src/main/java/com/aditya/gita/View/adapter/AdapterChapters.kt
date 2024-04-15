package com.aditya.gita.View.adapter

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aditya.gita.Models.ChaptersItem

import com.aditya.gita.databinding.ItemViewChaptersBinding

class AdapterChapters(val onChapterIVClicked: (ChaptersItem) -> Unit) : RecyclerView.Adapter<AdapterChapters.myViewHolder>() {

    inner class myViewHolder(val binding: ItemViewChaptersBinding) :RecyclerView.ViewHolder(binding.root){

    }

    val diffUtil=object :DiffUtil.ItemCallback<ChaptersItem>(){
        override fun areItemsTheSame(oldItem: ChaptersItem, newItem: ChaptersItem): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: ChaptersItem, newItem: ChaptersItem): Boolean {
        return oldItem==newItem
        }

    }

    val differ=AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
       return myViewHolder(
           ItemViewChaptersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {

        val chapter:ChaptersItem=differ.currentList[position]
        holder.binding.apply {
            chNo.text=chapter.chapter_number.toString()
            chTitle.text=chapter.name.toString()
            chDesc.text=chapter.chapter_summary_hindi.toString()
            countVerses.text=chapter.verses_count.toString()
        }
        holder.binding.linearLayout.setOnClickListener {
          onChapterIVClicked(chapter)
        }
    }
}