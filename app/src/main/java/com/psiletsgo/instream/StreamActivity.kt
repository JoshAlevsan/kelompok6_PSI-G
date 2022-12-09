package com.psiletsgo.instream

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.psiletsgo.instream.databinding.ActivityStreamBinding

class StreamActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStreamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityStreamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        val title = intent.getStringExtra("title")
        val detail = intent.getStringExtra("detail")
        val matkul = intent.getStringExtra("matkul")

        binding.title.text = title
        binding.detail.text = detail
        binding.matkul.text = matkul



    }
}