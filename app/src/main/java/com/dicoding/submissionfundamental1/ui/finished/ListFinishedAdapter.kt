package com.dicoding.submissionfundamental1.ui.finished

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissionfundamental1.data.remote.response.ListEventsItem
import com.dicoding.submissionfundamental1.databinding.ItemFinishedBinding
import com.dicoding.submissionfundamental1.ui.detail.DetailActivity
import com.dicoding.submissionfundamental1.ui.upcoming.ListUpcomingAdapter.Companion.EVENT_MEDIACOVER
import com.dicoding.submissionfundamental1.ui.upcoming.ListUpcomingAdapter.Companion.EVENT_NAME
import com.dicoding.submissionfundamental1.ui.upcoming.ListUpcomingAdapter.Companion.ID_EVENT

class ListFinishedAdapter: ListAdapter<ListEventsItem, ListFinishedAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    inner class MyViewHolder(private val binding: ItemFinishedBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listEvent: ListEventsItem){
            Glide
                .with(binding.imgFinished.context)
                .load(listEvent.imageLogo)
                .into(binding.imgFinished)
            binding.tvFinished.text = listEvent.name
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra(ID_EVENT, listEvent.id)
                intent.putExtra(EVENT_NAME, listEvent.name)
                intent.putExtra(EVENT_MEDIACOVER, listEvent.mediaCover)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFinishedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemEvent = getItem(position)
        holder.bind(itemEvent)
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

