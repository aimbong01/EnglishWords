package com.englishwords.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.englishwords.model.WordModel
import com.google.firebase.database.*

class SharedViewModel : ViewModel() {
    private var databaseReference: DatabaseReference
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val _data = MutableLiveData<ArrayList<WordModel>>()
    val data: LiveData<ArrayList<WordModel>>
        get() = _data

    var groupId = 1


    val wordList: ArrayList<WordModel> = ArrayList()


    init {
        databaseReference = database.reference.child("data/words")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                wordList.clear()
                for (postSnapshot in snapshot.children) {
                    val words: WordModel? = postSnapshot.getValue(WordModel::class.java)
                    wordList.add(words!!)
                }
                _data.value = wordList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("hata", "firebase")
            }
        })


    }

}

