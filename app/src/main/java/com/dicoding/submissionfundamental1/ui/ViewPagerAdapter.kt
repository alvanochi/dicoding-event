package com.dicoding.submissionfundamental1.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissionfundamental1.data.response.ListEventsItem
import com.dicoding.submissionfundamental1.databinding.ItemViewPagerBinding
import com.dicoding.submissionfundamental1.ui.ListUpcomingAdapter.Companion.ID_ITEM

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
                intent.putExtra(ID_ITEM, item.id)
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
