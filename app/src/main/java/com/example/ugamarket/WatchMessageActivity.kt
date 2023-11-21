package com.example.ugamarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class WatchMessageActivity : AppCompatActivity() {

    val adapter = MessageListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_message)

        init()
        showAll()
        //adapter.addItem("하나의 메시지")
    }

    fun init() {
        val recyclerView = findViewById<RecyclerView>(R.id.msgRecyclerView)
        //val adapter = PostListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }

    fun showAll() {
        val db: FirebaseFirestore = Firebase.firestore
        val msgCollectionRef = db.collection("messages")
        val myUid = Firebase.auth.currentUser?.uid // 나의 uid
        msgCollectionRef.whereEqualTo(
            "receiver_uid", myUid
        ).get().addOnSuccessListener {
            for (doc in it) {
                adapter.addItem("${doc["content"]}")
                init()
            }
        }

    }
}