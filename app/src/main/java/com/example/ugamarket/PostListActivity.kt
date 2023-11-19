package com.example.ugamarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PostListActivity : AppCompatActivity() {
    val adapter = PostListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)

        val radioGroupFilter = findViewById<RadioGroup>(R.id.filter)
        val radioButtonAll = findViewById<RadioButton>(R.id.radioButtonAll)
        val radioButtonOnSale = findViewById<RadioButton>(R.id.radioButtonOnSale)
        val radioButtonOnSold = findViewById<RadioButton>(R.id.radioButtonSold)


        radioGroupFilter.setOnCheckedChangeListener { radioGroup, checkid ->
            when (checkid) {
                radioButtonAll.id -> showAll() // 전체를 쿼리하여 보여주는 함수
                radioButtonOnSale.id -> showOnSale()   // 판매중을 쿼리하여 보여주는 함수
                radioButtonOnSold.id -> showSold()  // 판매완료를 쿼리하여 보여준는 함수
            }
        }
        radioGroupFilter.check(radioButtonAll.id)   // 필터의 기본값은 전체

        init()
        getData();
    }

    private fun getData() {
        val postListItem = PostListItem()
        adapter.addItem(postListItem)

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
    }

    fun showOnSale() {
        println("판매중을 쿼리하여 보여주는 함수")
    }

    fun showSold() {
        println("판매완료를 쿼리하여 보여준는 함수")
    }
}