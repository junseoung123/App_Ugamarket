package com.example.ugamarket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostListAdapter : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {
    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.post_list_item, parent, false)
        val viewHolder = ViewHolder(view)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView
        val titleText = view.findViewById<TextView>(R.id.textViewTitle)
        val saleOrNotText = view.findViewById<TextView>(R.id.textViewSaleOrNot)
        val priceText = view.findViewById<TextView>(R.id.textViewPrice)

        titleText.text = "제네시스 쿠페 완전 유사고 팝니다."
        saleOrNotText.text = "판매중"
        priceText.text = "8000000원"
    }

}