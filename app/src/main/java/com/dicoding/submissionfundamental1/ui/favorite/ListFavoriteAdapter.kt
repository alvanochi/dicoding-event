package com.dicoding.submissionfundamental1.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissionfundamental1.data.local.entity.EventsEntity
import com.dicoding.submissionfundamental1.databinding.ItemFavoriteBinding
import com.dicoding.submissionfundamental1.ui.detail.DetailActivity
import com.dicoding.submissionfundamental1.ui.upcoming.ListUpcomingAdapter.Companion.EVENT_MEDIACOVER
import com.dicoding.submissionfundamental1.ui.upcoming.ListUpcomingAdapter.Companion.EVENT_NAME
import com.dicoding.submissionfundamental1.ui.upcoming.ListUpcomingAdapter.Companion.ID_EVENT

class ListFavoriteAdapter: ListAdapter<EventsEntity, ListFavoriteAdapter.MyViewHolder>(
    DIFF_CALLBACK) {

    inner class MyViewHolder(private val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listEvent: EventsEntity){
            Glide
                .with(binding.imgFavorite.context)
                .load(listEvent.mediaCover)
                .into(binding.imgFavorite)
            binding.tvFavorite.text = listEvent.name
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra(ID_EVENT, listEvent.idEvent)
                intent.putExtra(EVENT_NAME, listEvent.name)
                intent.putExtra(EVENT_MEDIACOVER, listEvent.mediaCover)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemEvent = getItem(position)
        holder.bind(itemEvent)
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventsEntity>() {
            override fun areItemsTheSame(oldItem: EventsEntity, newItem: EventsEntity): Boolean {
                return oldItem == newItem
            }
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: EventsEntity, newItem: EventsEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}