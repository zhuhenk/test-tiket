package com.hendi.test_tiket.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hendi.test_tiket.R
import com.hendi.test_tiket.data.model.User
import com.hendi.test_tiket.data.model.UserResponse

/**
 * User list adapter
 * */
class UserAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_LOADING = 1
    }

    private var items = mutableListOf<User>()

    fun setData(userResponse: UserResponse) {
        items.clear()

        // ad items
        items.addAll(userResponse.items)

        // add load more flag
        if (items.size < userResponse.totalCount) {
            items.add(User(id = -1, login = "", avatarUrl = ""))
        }

        notifyDataSetChanged()
    }

    fun addData(userResponse: UserResponse) {
        // remove load more flag
        if (items.isNotEmpty() && items[items.size - 1].id == -1) {
            items.removeAt(items.size - 1)
        }

        // ad items
        items.addAll(userResponse.items)

        // add load more flag
        if (items.size < userResponse.totalCount) {
            items.add(User(id = -1, login = "", avatarUrl = ""))
        }

        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_ITEM -> UserViewHolder(
                layoutInflater.inflate(R.layout.item_user, parent, false)
            )
            else -> LoadMoreViewHolder(
                layoutInflater.inflate(R.layout.item_load_more, parent, false)
            )
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].id == -1) {
            TYPE_LOADING
        } else {
            TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserViewHolder) {
            holder.tvUser.text = items[position].login
            Glide.with(holder.itemView.context)
                .load(items[position].avatarUrl)
                .placeholder(R.drawable.img_octocat)
                .error(R.drawable.img_octocat)
                .circleCrop()
                .into(holder.ivUser)
        }
    }
}

/**
 * Item view holder
 * */
class UserViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
    val tvUser: TextView = itemView.findViewById(R.id.tv_user)
    val ivUser: ImageView = itemView.findViewById(R.id.iv_user)
}

/**
 * Load more view holder
 * */
class LoadMoreViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
}