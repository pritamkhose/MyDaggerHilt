package com.codingwithmitch.daggerhiltplayground.framework.presentation

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.codingwithmitch.daggerhiltplayground.R
import com.codingwithmitch.daggerhiltplayground.business.domain.models.Blog

class BlogAdapter : ListAdapter<Blog, ViewHolder>(Blog_COMPARATOR) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return BlogViewHolder.create(parent)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val blogItem = getItem(position)
        if (blogItem != null) {
            (holder as BlogViewHolder).bind(blogItem)
        }
    }

    companion object {
        private val Blog_COMPARATOR = object : DiffUtil.ItemCallback<Blog>() {
            override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean =
                oldItem == newItem
        }
    }
}

//the class is holding the list view
class BlogViewHolder(view: View, context: Context) : RecyclerView.ViewHolder(view) {

    private val title: TextView = view.findViewById(R.id.textViewTitle)
    private val body: TextView = view.findViewById(R.id.textViewBody)
    private val imageView: ImageView = view.findViewById(R.id.im_avatar)
    private var blog: Blog? = null
    private var mContext: Context

    init {
        view.setOnClickListener {
            blog?.title?.let { url ->
                Log.d("-->>", url)
            }
        }
        mContext = context
    }

    fun bind(blogItem: Blog?) {
        title.text = blogItem?.title
        body.text = blogItem?.body

        Glide.with(mContext)
            .load(blogItem?.image)
            .placeholder(R.mipmap.load)
            .error(R.mipmap.error)
            .into(imageView)
    }

    companion object {
        fun create(parent: ViewGroup): BlogViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
            return BlogViewHolder(view, parent.context)
        }
    }
}
