package com.example.takehomeassignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostAdapter (private val list: ArrayList<User>, val onClickDelete: (Int) -> Unit): RecyclerView.Adapter<PostAdapter.PostViewHolder>(){

    private var listData = list

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(postResponse: User, index: Int){
            with(itemView){

                val text = "${postResponse.id}. " +
                        "${postResponse.first_name} " +
                        "${postResponse.last_name}"
                val email = "email: ${postResponse.email}"

                var tvTitle: TextView = findViewById(R.id.tvTitle)
                var imageView: ImageView = findViewById(R.id.imageView)
                var tvBody: TextView = findViewById(R.id.tvBody)
                var btnDelete: Button = findViewById(R.id.btnDelete)

                tvTitle.text = text
                tvBody.text = email

                Glide.with(itemView.context).load(postResponse.avatar).centerCrop().into(imageView)

                btnDelete.setOnClickListener{onClickDelete(index)}
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(listData[position], position)
    }

    override fun getItemCount(): Int = listData.size

    fun setItems(items: ArrayList<User>){
        listData = items
        notifyDataSetChanged()
    }

}