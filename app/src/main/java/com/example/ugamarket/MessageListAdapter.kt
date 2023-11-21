package com.example.ugamarket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageListAdapter : RecyclerView.Adapter<MessageListAdapter.ViewHolder>() {

    val msgList = ArrayList<String>()    // 리사이클러뷰에 들어갈 게시물의 자료

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.message_list_item, parent, false)
        val viewHolder = ViewHolder(view)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return msgList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView
        val msgText = view.findViewById<TextView>(R.id.textViewMsg)
        msgText.text="${msgList.get(position)}"
    }


    fun addItem(msg: String) {
        msgList.add(msg)
        notifyDataSetChanged()
    }

    fun resetList() {
        msgList.clear()
        notifyDataSetChanged()
    }
}