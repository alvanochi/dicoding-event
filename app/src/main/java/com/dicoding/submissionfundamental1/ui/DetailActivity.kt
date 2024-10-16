package com.dicoding.submissionfundamental1.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.dicoding.submissionfundamental1.data.response.DetailEvent
import com.dicoding.submissionfundamental1.data.retrofit.ApiConfig
import com.dicoding.submissionfundamental1.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemId = intent.getIntExtra("id", -1)
        showDetailEvent(itemId)



    }


    private fun showDetailEvent(itemId: Int){
        val client = ApiConfig.getApiService().getEventDetail(itemId.toString())
        client.enqueue(object: Callback<DetailEvent> {
            override fun onResponse(call: Call<DetailEvent>, response: Response<DetailEvent>) {
                if (response.isSuccessful){
                    binding.progressBar.visibility = View.INVISIBLE
                    val result = response.body()?.event
                    Glide.with(binding.imgDetail.context)
                        .load(result?.mediaCover)
                        .into(binding.imgDetail)
                   binding.tvDetailTitle.text = result?.name
                    binding.tvDetailSummary.text = result?.summary
                    binding.tvDetailQuota.text = " ${result?.quota} - ${result?.registrants}"
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
                }else {
                    Log.e("Detail", "onFailure: ${response.message()}")
                }
            }


            override fun onFailure(call: Call<DetailEvent>, t: Throwable) {
                Log.e("Detail", "onFailure: ${t.message.toString()}")

            }
        })
    }
}