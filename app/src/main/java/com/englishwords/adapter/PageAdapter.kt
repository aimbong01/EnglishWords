package com.englishwords.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.englishwords.databinding.ItemPageBinding
import com.englishwords.model.WordModel
import com.englishwords.ui.PageFragmentDirections

class PageAdapter : RecyclerView.Adapter<PageAdapter.ViewHolder>() {

    var data = listOf<WordModel>()
    var pageList = arrayListOf<String>()
    var flag: String? = null

    fun run() {
        for (i in data) {
            if (flag != i.page) {
                pageList.add(i.page!!)
                flag = i.page
            }

        }
        Log.e("list", pageList.toString())
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pageList[position]
        holder.bind(item)
    }

    class ViewHolder private constructor(private val binding: ItemPageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(page: String) {

            binding.pageText.text = page

            binding.layout.setOnClickListener {
                it.findNavController().navigate(PageFragmentDirections.actionPageFragmentToWordFragment(page))
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPageBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount() = pageList.size

}