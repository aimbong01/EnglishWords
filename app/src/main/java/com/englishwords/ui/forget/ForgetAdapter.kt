package com.englishwords.ui.forget

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englishwords.databinding.ItemWordBinding
import com.englishwords.domain.model.WordModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ForgetAdapter(private val context: Context) : RecyclerView.Adapter<ForgetAdapter.ViewHolder>() {

    var data = listOf<WordModel>()
    var wordList = arrayListOf<WordModel>()
    var viewState = 0

    private var databaseReference: DatabaseReference
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    init {
        databaseReference = database.reference.child("data/words")
    }
    fun run() {
        wordList.clear()

        for (i in data) {
            if (i.learn == "0") {
                wordList.add(i)
            }
        }


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = wordList[position]
        holder.bind(item)

    }

    inner class ViewHolder(private val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(wordModel: WordModel) {
            if (viewState == 0) {
                binding.word.text = wordModel.english
                binding.pronounce.text = ("(" + wordModel.pronounce + ")")
            } else {
                binding.word.text = wordModel.turkish
                binding.pronounce.text = ""
            }

            binding.layout.setOnClickListener {

                if (binding.word.text == wordModel.english) {
                    binding.word.text = wordModel.turkish
                    binding.pronounce.text = ""

                } else {
                    binding.word.text = wordModel.english
                    binding.pronounce.text = ("(" + wordModel.pronounce + ")")
                }

            }
            val yes = context.resources.getIdentifier("com.englishwords:drawable/word_yes", null, null)
            val no = context.resources.getIdentifier("com.englishwords:drawable/word_no", null, null)

            if (wordModel.learn == "1") {
                binding.state.setBackgroundResource(yes)

            } else {
                binding.state.setBackgroundResource(no)

            }

            binding.state.setOnClickListener {

                databaseReference.get().addOnSuccessListener {
                    for (postSnapshot in it.children) {
                        val words: WordModel? = postSnapshot.getValue(WordModel::class.java)
                        if (words?.english == wordModel.english && words?.learn == "0") {
                            databaseReference.child("${postSnapshot.key}/learn").setValue("1")
                            binding.state.setBackgroundResource(yes)

                        }
                        if (words?.english == wordModel.english && words?.learn == "1") {
                            databaseReference.child("${postSnapshot.key}/learn").setValue("0")
                            binding.state.setBackgroundResource(no)


                        }

                    }
                }.addOnFailureListener {
                    Log.e("firebase", "Error getting data", it)
                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemWordBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount() = wordList.size

}