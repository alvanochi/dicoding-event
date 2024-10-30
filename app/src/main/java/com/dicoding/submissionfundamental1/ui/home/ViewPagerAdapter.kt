package com.dicoding.submissionfundamental1.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissionfundamental1.data.remote.response.ListEventsItem
import com.dicoding.submissionfundamental1.databinding.ItemViewPagerBinding
import com.dicoding.submissionfundamental1.ui.detail.DetailActivity
import com.dicoding.submissionfundamental1.ui.upcoming.ListUpcomingAdapter.Companion.EVENT_MEDIACOVER
import com.dicoding.submissionfundamental1.ui.upcoming.ListUpcomingAdapter.Companion.EVENT_NAME
import com.dicoding.submissionfundamental1.ui.upcoming.ListUpcomingAdapter.Companion.ID_EVENT

class ViewPagerAdapter : ListAdapter<ListEventsItem, ViewPagerAdapter.ViewHolder>(
    DIFF_CALLBACK
){

    inner class ViewHolder(private val binding: ItemViewPagerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListEventsItem) {
            binding.tvItemView.text = item.name
            Glide.with(binding.imgView.context)
                .load(item.imageLogo)
                .into(binding.imgView)
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra(ID_EVENT, item.id)
                intent.putExtra(EVENT_NAME, item.name)
                intent.putExtra(EVENT_MEDIACOVER, item.mediaCover)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
