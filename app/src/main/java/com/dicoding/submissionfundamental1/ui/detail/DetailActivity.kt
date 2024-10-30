package com.dicoding.submissionfundamental1.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.dicoding.submissionfundamental1.R
import com.dicoding.submissionfundamental1.ViewModelFactory
import com.dicoding.submissionfundamental1.data.local.entity.EventsEntity
import com.dicoding.submissionfundamental1.databinding.ActivityDetailBinding
import com.dicoding.submissionfundamental1.ui.upcoming.ListUpcomingAdapter.Companion.EVENT_MEDIACOVER
import com.dicoding.submissionfundamental1.ui.upcoming.ListUpcomingAdapter.Companion.EVENT_NAME
import com.dicoding.submissionfundamental1.ui.upcoming.ListUpcomingAdapter.Companion.ID_EVENT

class DetailActivity : AppCompatActivity(){
    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var eventsEntity: EventsEntity
    private var isFav : Boolean = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventId = intent.getIntExtra(ID_EVENT, -1)
        val eventName = intent.getStringExtra(EVENT_NAME) ?: ""
        val eventMediaCover = intent.getStringExtra(EVENT_MEDIACOVER) ?: ""


        eventsEntity = EventsEntity(eventId, eventName, eventMediaCover)
        detailViewModel.getFavEventById(eventId).observe(this) { result ->
            if (result != null) {
                isFav = true
                binding.btnFav.setImageResource(R.drawable.favorite_event_filled)
            } else {
                isFav = false
                binding.btnFav.setImageResource(R.drawable.favorite_event)
            }
        }

        detailViewModel.detailEvent.observe(this){ result ->

            binding.progressBar.visibility = View.INVISIBLE
            Glide.with(binding.imgDetail.context)
                .load(result?.mediaCover)
                .into(binding.imgDetail)
            binding.tvDetailTitle.text = result?.name
            binding.tvDetailSummary.text = result?.summary
            binding.tvDetailQuota.text = " ${result?.registrants?.let { result.quota.minus(it) }}"
            binding.tvDetailDate.text = result?.beginTime
            binding.tvOwnerName.text = result?.ownerName
            binding.tvDetailDesc.text = HtmlCompat.fromHtml(
                result?.description.toString(),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            binding.btnRegister.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(result?.link)
                }
                startActivity(intent)
            }
            binding.btnFav.setOnClickListener {
                if (isFav) {
                    Toast.makeText(this, "Berhasil hapus event!", Toast.LENGTH_SHORT).show()
                    detailViewModel.deleteEvent(eventsEntity.idEvent)
                    isFav = false
                } else {
                    Toast.makeText(this, "Berhasil tambahkan event!", Toast.LENGTH_SHORT).show()
                    detailViewModel.saveEvent(listOf(eventsEntity))
                    isFav = true
                }
            }
        }

        detailViewModel.showDetailEvent(eventId)


        detailViewModel.isLoading.observe(this) {
            setLoading(it)
        }
    }




    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}