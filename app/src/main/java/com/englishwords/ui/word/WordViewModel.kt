package com.englishwords.ui.word

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.englishwords.domain.model.WordModel
import com.google.firebase.database.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(database: FirebaseDatabase) : ViewModel() {
    private var databaseReference: DatabaseReference

    private val _data = MutableLiveData<ArrayList<WordModel>>()
    val data: LiveData<ArrayList<WordModel>>
        get() = _data

    init {
        databaseReference = database.reference.child("data/words")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val wordList: ArrayList<WordModel> = ArrayList()
                for (postSnapshot in snapshot.children) {
                    val words = postSnapshot.getValue(WordModel::class.java)
                    wordList.add(words!!)
                }
                _data.value = wordList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("hata", "firebase")
            }
        })
    }

    fun setLearn(wordModel: WordModel) {
        data.value?.let { words ->
            for ((index, word) in words.withIndex()) {
                if (word.english == wordModel.english && word.learn == "0") {
                    databaseReference.child("${index}/learn").setValue("1")
                }
                if (word.english == wordModel.english && word.learn == "1") {
                    databaseReference.child("${index}/learn").setValue("0")
                }
            }
        }
    }
}

