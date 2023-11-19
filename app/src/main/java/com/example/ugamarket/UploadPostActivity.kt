package com.example.ugamarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class UploadPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_post)

        val editTextTitle = findViewById<EditText>(R.id.editTextTitle)
        val editTextPrice = findViewById<EditText>(R.id.editTextPrice)
        val editTextContent = findViewById<EditText>(R.id.editTextContent)
        val buttonUpload = findViewById<Button>(R.id.buttonUpload)

        buttonUpload.setOnClickListener {
            val title = editTextTitle.text.toString()
            val price = editTextPrice.text.toString()
            val content = editTextContent.text.toString()

            if (title.equals("") || price.equals("") || content.equals("")) { // 입력 안한 경우
                Toast.makeText(this, "입력 하세요.", Toast.LENGTH_SHORT).show()
            } else {
                // 입력한 경우
                doUpload(title, price.toInt(), content)   // 회원 가입
            }
        }
    }

    private fun doUpload(title: String, price: Int, content: String) {
        val db: FirebaseFirestore = Firebase.firestore
        val usersCollectionRef = db.collection("posts")
        val userMap = hashMapOf(
            "title" to title,
            "price" to price,
            "content" to content,
            "sold" to false,
            "uid" to Firebase.auth.currentUser?.uid
        )
        usersCollectionRef.add(userMap).addOnSuccessListener {
            println("posts 컬렉션에 추가함")
            finish()
        }.addOnFailureListener {
            println("posts 컬렉션에 추가 실패 함")
        }
    }
}