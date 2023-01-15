package com.englishwords.ui.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.englishwords.databinding.FragmentPageBinding
import com.englishwords.domain.model.WordModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PageFragment : Fragment() {
    private lateinit var binding: FragmentPageBinding
    private lateinit var adapter: PageAdapter
    private val viewModel: PageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPageBinding.inflate(inflater)

        adapter = PageAdapter()

        viewModel.data.observe(viewLifecycleOwner) {
            adapter.data = bindData(it)
            adapter.notifyDataSetChanged()
        }

        binding.button.setOnClickListener {
            it.findNavController().navigate(PageFragmentDirections.actionPageFragmentToForgetFragment())
        }
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    private fun bindData(words: List<WordModel>): List<Int> {
        val pages: ArrayList<Int> = arrayListOf()
        if (words.isNotEmpty()) {
            val groupedList = words.chunked(40)
            for (i in groupedList.indices) {
                pages.add(i)
            }
        }
        return pages
    }
}