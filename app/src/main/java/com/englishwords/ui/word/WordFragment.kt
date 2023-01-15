package com.englishwords.ui.word

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.englishwords.databinding.FragmentWordBinding
import com.englishwords.domain.model.WordModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordFragment : Fragment() {
    private lateinit var binding: FragmentWordBinding
    private lateinit var adapter: WordAdapter
    private val viewModel: WordViewModel by viewModels()
    private val args: WordFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentWordBinding.inflate(inflater)

        adapter = WordAdapter{
            viewModel.setLearn(it)
        }
        binding.recyclerView.adapter = adapter

        val helper: SnapHelper = PagerSnapHelper()
        helper.attachToRecyclerView(binding.recyclerView)

        viewModel.data.observe(viewLifecycleOwner) { data ->
            adapter.data = bindData(data, "1")
            adapter.notifyDataSetChanged()
            buttonListener {
                adapter.data = bindData(data, it)
                binding.recyclerView.smoothScrollToPosition(0)
                adapter.notifyDataSetChanged()
            }
        }

        binding.button6.setOnClickListener {
            adapter.viewState = true
            adapter.notifyDataSetChanged()
        }
        binding.button7.setOnClickListener {
            adapter.viewState = false
            adapter.notifyDataSetChanged()

        }
        return binding.root
    }

    private fun bindData(words: List<WordModel>, type: String): List<WordModel> {
        val filteredList = words.chunked(40)[args.page.toInt()]
        if (type == "all") {
            return filteredList
        } else {
            val wordsList = filteredList.chunked(10)
            when (type) {
                "1" -> return wordsList[0]
                "2" -> return wordsList[1]
                "3" -> return wordsList[2]
                "4" -> return wordsList[3]
                else -> return emptyList()
            }
        }
    }

    private fun buttonListener(onClick: (String) -> Unit) {
        binding.button1.setOnClickListener {
            onClick("1")
        }
        binding.button2.setOnClickListener {
            onClick("2")
        }
        binding.button3.setOnClickListener {
            onClick("3")
        }
        binding.button4.setOnClickListener {
            onClick("4")
        }
        binding.button5.setOnClickListener {
            onClick("all")
        }
    }

}