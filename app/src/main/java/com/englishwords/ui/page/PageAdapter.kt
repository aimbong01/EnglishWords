package com.englishwords.ui.page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.englishwords.databinding.ItemPageBinding

class PageAdapter : RecyclerView.Adapter<PageAdapter.ViewHolder>() {

    var data = listOf<Int>()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    class ViewHolder(private val binding: ItemPageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(page: Int) {
            binding.pageText.text = (page + 1).toString()
            binding.layout.setOnClickListener {
                it.findNavController().navigate(PageFragmentDirections.actionPageFragmentToWordFragment(page.toString()))
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPageBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = data.size

}