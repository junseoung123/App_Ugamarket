package com.example.ugamarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_post)

        val postId = intent.getStringExtra("postId")

        val textViewTitle = findViewById<TextView>(R.id.textViewTitle)
        val textViewName = findViewById<TextView>(R.id.textViewName)
        val editTextPrice = findViewById<EditText>(R.id.editTextPrice)
        val radioGropSaleState = findViewById<RadioGroup>(R.id.radioGroupSaleState)
        val radioButtonSale = findViewById<RadioButton>(R.id.radioButtonSale)
        val radioButtonSold = findViewById<RadioButton>(R.id.radioButtonSold)
        val textViewContent = findViewById<TextView>(R.id.textViewContent)

        val db: FirebaseFirestore = Firebase.firestore
        val itemsCollectionRef = db.collection("posts")
        val usersCollectionRef = db.collection("users")
        val documentRef = itemsCollectionRef.document(postId.toString())
        documentRef.get().addOnSuccessListener {
            textViewTitle.text = it["title"].toString()
            val uid = it["uid"].toString()
            var name = ""
            //val user =
            usersCollectionRef.whereEqualTo("uid", uid).get().addOnSuccessListener {
                for (doc in it) {
                    name = doc["name"].toString()
                    textViewName.text = "판매자 이름 : ${name}"
                }
            }
            editTextPrice.setText(it["price"].toString())
            if (it["sold"] as Boolean == true)
                radioGropSaleState.check(radioButtonSold.id)//판매완료일 경우 판매완료 선택이 기본값
            else
                radioGropSaleState.check(radioButtonSale.id)//판매중일 경우 판매중 선택이 기본값
            textViewContent.text = it["content"].toString()
        }

        val buttonEdit = findViewById<Button>(R.id.buttonEdit)
        buttonEdit.setOnClickListener {

            if (editTextPrice.text.toString().equals("")) { // 입력 안한 경우
                Toast.makeText(this, "가격을 입력 하세요.", Toast.LENGTH_SHORT).show()
            } else {
                // 입력한 경우
                documentRef.update("price", editTextPrice.text.toString().toInt())
                if (radioButtonSale.isChecked)
                    documentRef.update("sold", false)//판매중에 체크된 경우
                else {
                    documentRef.update("sold", true)//판매완료에 체크된 경우
                }
                finish()
            }
        }
    }
}