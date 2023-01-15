package com.englishwords.ui.word

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englishwords.R
import com.englishwords.databinding.ItemWordBinding
import com.englishwords.domain.model.WordModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class WordAdapter(private val onClickLearnListener: (WordModel) -> Unit) : RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    var data = listOf<WordModel>()
    var viewState = true

    private var databaseReference: DatabaseReference
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    init {
        databaseReference = database.reference.child("data/words")
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

    }

    inner class ViewHolder(private val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(wordModel: WordModel) {
            if (viewState) {
                binding.word.text = wordModel.english
                binding.pronounce.text = ("(" + wordModel.pronounce + ")")
            } else {
                binding.word.text = wordModel.turkish
                binding.pronounce.text = ""
            }
            var visibility = true
            binding.layout.setOnClickListener {
                if (visibility) {
                    binding.word.text = wordModel.turkish
                    binding.pronounce.text = ""
                } else {
                    binding.word.text = wordModel.english
                    binding.pronounce.text = ("(" + wordModel.pronounce + ")")
                }
                visibility = !visibility
            }

            if (wordModel.learn == "1") {
                binding.state.setBackgroundResource(R.drawable.word_yes)

            } else {
                binding.state.setBackgroundResource(R.drawable.word_no)

            }

            binding.state.setOnClickListener {
                onClickLearnListener(wordModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemWordBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = data.size

}
