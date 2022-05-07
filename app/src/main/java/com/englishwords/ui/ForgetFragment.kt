package com.englishwords.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.englishwords.R
import com.englishwords.adapter.ForgetAdapter
import com.englishwords.adapter.WordAdapter
import com.englishwords.databinding.FragmentForgetBinding

class ForgetFragment : Fragment() {

    private lateinit var binding: FragmentForgetBinding
    private lateinit var adapter: ForgetAdapter
    private val viewModel: SharedViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgetBinding.inflate(inflater)

        adapter = ForgetAdapter(requireContext())
        binding.recyclerView.adapter = adapter

        val helper: SnapHelper = PagerSnapHelper()
        helper.attachToRecyclerView(binding.recyclerView)

        viewModel.data.observe(viewLifecycleOwner) {
            adapter.data = it
            adapter.run()
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

}