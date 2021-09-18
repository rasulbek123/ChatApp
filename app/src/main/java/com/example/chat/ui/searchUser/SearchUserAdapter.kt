package com.example.chat.ui.searchUser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chat.R
import com.example.chat.data.model.User
import com.example.chat.databinding.ItemSearchUserBinding

class SearchUserAdapter : RecyclerView.Adapter<SearchUserAdapter.SearchUserHolder>() {
    var models:List<User> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    inner class SearchUserHolder(private val binding: ItemSearchUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populeatModel(user: User){
            Glide.with(binding.root.context)
                .load(user.image)
                .centerCrop()
                .into(binding.userImage)
            binding.userItemName.text = user.username.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_user,parent,false)
        return SearchUserHolder(ItemSearchUserBinding.bind(view))
    }

    override fun onBindViewHolder(holder: SearchUserHolder, position: Int) {
        holder.populeatModel(models[position])
    }

    override fun getItemCount(): Int = models.size
}