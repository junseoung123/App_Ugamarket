package com.example.ugamarket

import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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

        //판매 글 등록 버튼
        val buttonPost = findViewById<Button>(R.id.buttonPost)
        buttonPost.setOnClickListener {
            val intent = Intent(this, UploadPostActivity::class.java)
            startActivity(intent)
        }


        val radioGroupFilter = findViewById<RadioGroup>(R.id.filter)
        val radioButtonAll = findViewById<RadioButton>(R.id.radioButtonAll)
        val radioButtonSale = findViewById<RadioButton>(R.id.radioButtonOnSale)
        val radioButtonSold = findViewById<RadioButton>(R.id.radioButtonSold)


        //init()
        //radioGroupFilter.check(radioButtonAll.id)   // 필터의 기본값은 전체
        radioButtonAll.setOnClickListener {
            showAll()
        }
        radioButtonSale.setOnClickListener {
            showOnSale()
        }
        radioButtonSold.setOnClickListener {
            showSold()
        }

        //showAll()
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
        adapter.resetList()
        val db: FirebaseFirestore = Firebase.firestore
        val postsCollectionRef = db.collection("posts")
        postsCollectionRef.whereEqualTo(
            "sold", false
        ).get().addOnSuccessListener {
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

    fun showSold() {
        println("판매완료를 쿼리하여 보여준는 함수")
        adapter.resetList()
        val db: FirebaseFirestore = Firebase.firestore
        val postsCollectionRef = db.collection("posts")
        postsCollectionRef.whereEqualTo(
            "sold", true
        ).get().addOnSuccessListener {
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

    override fun onResume() {
        super.onResume()
        val radioGroupFilter = findViewById<RadioGroup>(R.id.filter)
        val radioButtonAll = findViewById<RadioButton>(R.id.radioButtonAll)
        val radioButtonSale = findViewById<RadioButton>(R.id.radioButtonOnSale)
        val radioButtonSold = findViewById<RadioButton>(R.id.radioButtonSold)
        radioGroupFilter.check(radioButtonAll.id)
        showAll()
    }

}