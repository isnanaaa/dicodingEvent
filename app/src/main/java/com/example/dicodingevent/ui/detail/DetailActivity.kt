package com.example.dicodingevent.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.dicodingevent.R
import com.example.dicodingevent.data.response.Event

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var imageView: ImageView
    private lateinit var tvEventTitle: TextView
    private lateinit var tvOwner: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvQuota: TextView
    private lateinit var tvDesc: TextView
    private lateinit var tvRegist: TextView
    private lateinit var tvCategory: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnRegist: Button
    private lateinit var eventLink: String

    private lateinit var event: Event

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.title = "Detail Event"

        imageView = findViewById(R.id.img)
        tvEventTitle = findViewById(R.id.events_name)
        tvOwner = findViewById(R.id.owner)
        tvTime = findViewById(R.id.time)
        tvQuota = findViewById(R.id.kuota)
        tvDesc = findViewById(R.id.descript)
        tvRegist = findViewById(R.id.regisrants)
        tvCategory = findViewById(R.id.category)
        progressBar = findViewById(R.id.progressBar)
        btnRegist = findViewById(R.id.btnregist)

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        val eventID = intent.getIntExtra("EVENT_ID", -1)
        Log.d("DetailActivity", "Received Event ID: $eventID")

        if (eventID != -1){
            viewModel.fetchDetail(eventID)
        } else{
            Log.e("DetailActivity", "Invalid or null event ID")
        }

        viewModel.events.observe(this){ detailResponse ->
            if (detailResponse?.event != null){
                event = detailResponse.event
                displayDetail(event)
            }
        }

        viewModel.isLoad.observe(this){ isLoad ->
            progressBar.visibility = if (isLoad) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(this){ errorMessage ->
            if (!errorMessage.isNullOrEmpty()){
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        btnRegist.setOnClickListener{
            if (this::eventLink.isInitialized){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(eventLink))
                startActivity(intent)
            } else{
                Toast.makeText(this, "Event link not available", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayDetail(event: Event){
        tvEventTitle.text = event.name
        tvOwner.text = event.ownerName
        tvTime.text = "${event.beginTime} - ${event.endTime}"
        tvQuota.text = "${event.quota} leave"
        tvDesc.text = HtmlCompat.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        tvRegist.text = "Registrants: ${event.quota}"
        tvCategory.text = "Category: ${event.quota}"
        eventLink = event.link

        Glide.with(this)
            .load(event.mediaCover)
            .into(imageView)
    }
}