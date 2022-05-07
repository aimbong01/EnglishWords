package com.englishwords.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.englishwords.R
import com.englishwords.adapter.WordAdapter
import com.englishwords.databinding.FragmentWordBinding

class WordFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentWordBinding
    private val viewModel: SharedViewModel by viewModels()

    private lateinit var adapter: WordAdapter
    private val args: WordFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word, container, false)
        binding.lifecycleOwner = this


        adapter = WordAdapter(requireContext())
        adapter.page = args.page
        binding.recyclerView.adapter = adapter

        val helper: SnapHelper = PagerSnapHelper()
        helper.attachToRecyclerView(binding.recyclerView)

        viewModel.data.observe(viewLifecycleOwner) {
            adapter.data = it
            adapter.run()
            adapter.notifyDataSetChanged()
        }

        binding.button1.setOnClickListener(this)
        binding.button2.setOnClickListener(this)
        binding.button3.setOnClickListener(this)
        binding.button4.setOnClickListener(this)
        binding.button5.setOnClickListener(this)
        binding.button6.setOnClickListener {
            adapter.viewState = 0
            adapter.notifyDataSetChanged()
        }
        binding.button7.setOnClickListener {
            adapter.viewState = 1
            adapter.notifyDataSetChanged()

        }





        return binding.root
    }

    override fun onClick(v: View?) {

        adapter = WordAdapter(requireContext())
        adapter.page = args.page
        binding.recyclerView.adapter = adapter

        when (v?.id) {
            binding.button1.id -> adapter.group = "1"
            binding.button2.id -> adapter.group = "2"
            binding.button3.id -> adapter.group = "3"
            binding.button4.id -> adapter.group = "4"
            binding.button5.id -> adapter.group = "all"


        }
        viewModel.data.observe(viewLifecycleOwner) {
            adapter.data = it
            adapter.run()
            adapter.notifyDataSetChanged()
        }
        Log.e("click", "run")

    }

}