package com.hendev.instahen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_row.view.*

class FeedAdaptor (val postList: ArrayList<Post>): RecyclerView.Adapter<FeedAdaptor.PostHolder>() {

    class PostHolder(x: View) : RecyclerView.ViewHolder(x) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row,parent,false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = postList[position]
        //holder.itemView.rowTxtMail.text = postList[position].kullanici
        holder.itemView.rowTxtMail.text = post.kullanici
        holder.itemView.rowTxtYorum.text = post.yorum
        Picasso.get().load(post.gorsleUrl).into(holder.itemView.rowImage)
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}