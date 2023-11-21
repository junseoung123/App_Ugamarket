package com.example.ugamarket

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class PostListAdapter : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    val postList = ArrayList<PostListItem>()    // 리사이클러뷰에 들어갈 게시물의 자료

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.post_list_item, parent, false)
        val viewHolder = ViewHolder(view)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return postList.size
        //return 5
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView
        val titleText = view.findViewById<TextView>(R.id.textViewMsg)
        val saleOrNotText = view.findViewById<TextView>(R.id.textViewSaleOrNot)
        val priceText = view.findViewById<TextView>(R.id.textViewPrice)
        /*
                titleText.text = "제네시스 쿠페 완전 유사고 팝니다."
                saleOrNotText.text = "판매중"
                priceText.text = "8000000원"

         */
        titleText.text = postList.get(position).title
        if (postList.get(position).sold == true)
            saleOrNotText.text = "판매완료"
        else
            saleOrNotText.text = "판매중"
        priceText.text = "${postList.get(position).price}원"

        view.setOnClickListener {
            //Toast.makeText(view.context, "${postList.get(position).postId}", Toast.LENGTH_SHORT) .show()

            if (Firebase.auth.currentUser?.uid.equals(postList.get(position).uid)) {
                // 본인의 글일 경우
                val intent = Intent(view.context, EditPostActivity::class.java)
                intent.putExtra("postId", postList.get(position).postId);
                view.context.startActivity(intent)
            } else {
                // 다른 사람 글일 경우
                val intent = Intent(view.context, ViewPostActivity::class.java)
                intent.putExtra("postId", postList.get(position).postId);
                view.context.startActivity(intent)
            }
        }
    }


    fun addItem(data: PostListItem) {
        postList.add(data)
        notifyDataSetChanged()
    }

    fun resetList() {
        postList.clear()
        notifyDataSetChanged()
    }
}