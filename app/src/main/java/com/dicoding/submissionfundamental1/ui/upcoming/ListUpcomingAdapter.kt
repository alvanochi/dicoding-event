package com.dicoding.submissionfundamental1.ui.upcoming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissionfundamental1.data.remote.response.ListEventsItem
import com.dicoding.submissionfundamental1.databinding.ItemUpcomingBinding



class ListUpcomingAdapter(private val listener: OnItemClickListener): ListAdapter<ListEventsItem, ListUpcomingAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {

    interface OnItemClickListener {
        fun onItemClick(item: ListEventsItem)
    }


    inner class MyViewHolder(private val binding: ItemUpcomingBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listEvent: ListEventsItem){
            Glide
                .with(binding.imgUpcoming.context)
                .load(listEvent.mediaCover)
                .into(binding.imgUpcoming)
            binding.tvUpcoming.text = listEvent.name

            binding.root.setOnClickListener {
                listener.onItemClick(listEvent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemEvent = getItem(position)
        holder.bind(itemEvent)

    }
    companion object {
        const val ID_EVENT = "id"
        const val EVENT_NAME = "name"
        const val EVENT_MEDIACOVER = "mediaCover"
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

