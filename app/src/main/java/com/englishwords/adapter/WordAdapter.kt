package com.englishwords.adapter

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.englishwords.model.WordModel
import com.englishwords.databinding.ItemWordBinding
import com.google.firebase.database.*
import kotlinx.coroutines.launch

class WordAdapter(private val context: Context) : RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    var data = listOf<WordModel>()
    var wordList = arrayListOf<WordModel>()
    lateinit var page: String
    var group: String = "1"
    var viewState = 0

    private var databaseReference: DatabaseReference
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    init {
        databaseReference = database.reference.child("data/words")
    }

    fun run() {
        wordList.clear()
        if (group != "all") {
            for (i in data) {
                if (i.group == group && i.page == page && i.english != "") {
                    wordList.add(i)
                }
            }
        } else {
            for (i in data) {
                if (i.page == page && i.english != "") {
                    wordList.add(i)
                }
            }
        }


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = wordList[position]
        holder.bind(item)

    }

    inner class ViewHolder(private val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(wordModel: WordModel) {
            if(viewState==0){
                binding.word.text = wordModel.english
                binding.pronounce.text = ("(" + wordModel.pronounce + ")")
            }else{
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

                /*databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (postSnapshot in snapshot.children) {
                            val words: WordModel? = postSnapshot.getValue(WordModel::class.java)
                            if (words?.english == wordModel.english && words?.learn=="0") {
                                databaseReference.child("${postSnapshot.key}/learn").setValue("1")
                                Log.e("0","calis")

                            }
                            if (words?.english == wordModel.english && words?.learn=="1") {
                                Log.e("1","calis")
                                databaseReference.child("${postSnapshot.key}/learn").setValue("0")

                            }

                        }

                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e("hata", "firebase")
                    }
                })*/
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
