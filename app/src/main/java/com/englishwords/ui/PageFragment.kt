package com.englishwords.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.englishwords.R
import com.englishwords.adapter.PageAdapter
import com.englishwords.databinding.FragmentPageBinding


class PageFragment : Fragment() {
    private lateinit var binding: FragmentPageBinding
    private lateinit var adapter: PageAdapter


    private val viewModel: SharedViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_page, container, false)

        binding.lifecycleOwner = this

        adapter = PageAdapter()

        viewModel.data.observe(viewLifecycleOwner){
            adapter.data = it
            adapter.run()
            adapter.notifyDataSetChanged()
        }

        binding.button.setOnClickListener {
            it.findNavController().navigate(PageFragmentDirections.actionPageFragmentToForgetFragment())
        }



        binding.recyclerView.adapter = adapter


        return binding.root
    }

}