package com.example.ugamarket

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class ViewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_post)

        val postId = intent.getStringExtra("postId")

        val textViewTitle = findViewById<TextView>(R.id.textViewTitle)
        val textViewContent = findViewById<TextView>(R.id.textViewContent)
        val textViewPrice = findViewById<TextView>(R.id.textViewPrice)
        val textViewState = findViewById<TextView>(R.id.textViewSaleState)
        val textViewName = findViewById<TextView>(R.id.textViewName)

        val db: FirebaseFirestore = Firebase.firestore
        val itemsCollectionRef = db.collection("posts")
        val usersCollectionRef = db.collection("users")
        val documentRef = itemsCollectionRef.document(postId.toString())
        documentRef.get().addOnSuccessListener {
            textViewTitle.text = it["title"].toString()
            textViewContent.text = it["content"].toString()
            textViewPrice.text = "가격:${it["price"].toString()}원"
            if (it["sold"] as Boolean == true)
                textViewState.text = "판매완료"//판매완료일 경우
            else
                textViewState.text = "판매중"//판매중일 경우

            textViewContent.text = it["content"].toString()

            val uid = it["uid"].toString()
            var name = ""
            //val user =
            usersCollectionRef.whereEqualTo("uid", uid).get().addOnSuccessListener {
                for (doc in it) {
                    name = doc["name"].toString()
                    textViewName.text = "판매자 이름 : ${name}"
                }
            }
        }

        val buttonSendMsg = findViewById<Button>(R.id.buttonSendMsg)
        buttonSendMsg.setOnClickListener {
            //판매자에게 메시지 보내기

        }
    }
}

