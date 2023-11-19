package com.example.ugamarket

import android.content.ClipData.Item
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class PostListActivity : AppCompatActivity() {
    val adapter = PostListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)

        val radioGroupFilter = findViewById<RadioGroup>(R.id.filter)
        val radioButtonAll = findViewById<RadioButton>(R.id.radioButtonAll)
        val radioButtonOnSale = findViewById<RadioButton>(R.id.radioButtonOnSale)
        val radioButtonOnSold = findViewById<RadioButton>(R.id.radioButtonSold)


        radioGroupFilter.check(radioButtonAll.id)   // 필터의 기본값은 전체
        init()
        radioGroupFilter.setOnCheckedChangeListener { radioGroup, checkid ->
            when (checkid) {
                radioButtonAll.id -> showAll() // 전체를 쿼리하여 보여주는 함수
                radioButtonOnSale.id -> showOnSale()   // 판매중을 쿼리하여 보여주는 함수
                radioButtonOnSold.id -> showSold()  // 판매완료를 쿼리하여 보여준는 함수
            }
        }
        showAll()
        //getData();
    }

    private fun getData(data: PostListItem) {
        val postListItem = PostListItem()
        adapter.addItem(data)

    }


    fun init() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //val adapter = PostListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }

    fun showAll() {
        println("전체를 쿼리하여 보여주는 함수")
        adapter.resetList()
        val db: FirebaseFirestore = Firebase.firestore
        val postsCollectionRef = db.collection("posts")
        postsCollectionRef.get().addOnSuccessListener {
            val posts = mutableListOf<Item>()
            for (doc in it) {
                val postListItem = PostListItem()
                val itemID = doc.id
                postsCollectionRef.document(itemID).get().addOnSuccessListener {

                    postListItem.postId = doc.id
                    postListItem.uid = it["uid"].toString()
                    postListItem.title = it["title"].toString()
                    postListItem.sold = it["sold"] as Boolean
                    postListItem.price = it["price"] as Number
                    postListItem.content = it["content"].toString()

                    getData(postListItem)
                    init()
                    println("쿼리해옴 : ${postListItem.title} ${it["sold"]}")
                }
            }
        }
    }

    fun showOnSale() {
        println("판매중을 쿼리하여 보여주는 함수")
    }

    fun showSold() {
        println("판매완료를 쿼리하여 보여준는 함수")
    }
}