package com.example.urbandictionary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandictionary.databinding.ListItemBinding
import com.example.urbandictionary.domain.Word

class WordAdapter : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    var wordlist: List<Word> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return WordViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return wordlist.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = wordlist[position]
        holder.bind(word)
    }

    class WordViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(word: Word) {
            binding.itemWord = word
            binding.executePendingBindings()
        }
    }
}
