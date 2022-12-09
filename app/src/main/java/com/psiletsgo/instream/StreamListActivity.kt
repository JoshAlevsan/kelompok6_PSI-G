package com.psiletsgo.instream

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.psiletsgo.instream.databinding.ActivityStreamListBinding
import com.psiletsgo.instream.CustomAdapter
import com.psiletsgo.instream.Stream
import com.psiletsgo.instream.StreamActivity
import kotlinx.android.synthetic.main.activity_stream_list.*

class StreamListActivity : AppCompatActivity() {

    private  lateinit var binding : ActivityStreamListBinding
    private lateinit var streamArrayList: ArrayList<Stream>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStreamListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        val title = arrayOf(
            "Pengembangan Full Stack",
            "Interaktif Media",
            "Rekayasa Jaringan",
            "Keamanan Siber",
            "Komputasi Cerdas",
            "Sains Data")

        val info = arrayOf(
            "klik untuk mengetahui Full Stack",
            "klik untuk mengetahui Interaktif Media",
            "klik untuk mengetahui Rekayasa Jaringan",
            "klik untuk mengetahui Keamanan Siber",
            "klik untuk mengetahui Komputasi Cerdas",
            "klik untuk mengetahui Sains Data")

        val detail = arrayOf(
            "Stream ini memberikan pengayaan kompetensi bagi mahasiswa dalam bidang pengembangan full stack dengan bahan kajian utama yaitu : " +
                    "Pengelolaan sumber daya proyek perangkat lunak, Penerapan pola–pola perancangan dalam pengembangan sistem perangkat lunak, " +
                    "dan Pemanfaatan webservice dan arsitektur microservice dalam pembangunan sistem perangkat lunak.",
            "Stream ini memberikan pengayaan kompetensi bagi mahasiswa dalam bidang interaksi manusia " +
                    "dan komputer serta penguasaan terhadap berbagai macam teknologi dan platform modern, antara " +
                    "lain perangkat bergerak (mobile), gim (game), realitas berimbuh dan maya (augmented and virtual" +
                    "reality).",
            "Stream ini memberikan pengayaan kompetensi bagi mahasiswa dalam bidang rekayasa dan\n" +
                    "pengelolaan jaringan komputer serta komputasi awan.",
            "Stream ini memberikan pengayaan kompetensi bagi mahasiswa dalam bidang keamanan siber dengan bahan kajian utama yaitu: " +
                    "Asesmen dan pengetesan keamanan dari sebuah sistem dan" +
                    "Ancaman dan mekanisme pengamanan komunikasi pada jaringan komputer.",
            "Stream ini memberikan pengayaan kompetensi bagi mahasiswa dalam bidang komputasi cerdas." +
                    "Beberapa bahan kajian utama yang dibahas pada stream ini antara lain: " +
                    "Pengolahan data seperti data citra, teks, dan data digital lainnya.",
            "Stream ini memberikan pengayaan kompetensi bagi mahasiswa dalam bidang Sains Data." +
                    "Beberapa bahan kajian utama yang dibahas pada stream ini antara lain: " +
                    "Pengantar terkait Sains Data dan Statistika Inferensi.")

        val matkul = arrayOf(
            "Rekayasa Perangkat Lunak, Pemrograman Sistem Interaktif, Pola Perancangan",
            "Sistem Multimedia, Grafika Komputer, Pemrograman Gim",
            "Jaringan Komputer, IoT, Komputasi Awan",
            "Keamanan Informasi, Kriptografi, Forensik Digital",
            "Algoritma Struktur Data, Data Mining, Logika Fuzzy",
            "Jaringan Saraf Tiruan, Pengantar Sains Data, Visualisasi Data")

        streamArrayList = ArrayList()
        for (i in title.indices){
            val stream = Stream(title[i], info[i], detail[i], matkul[i])
            streamArrayList.add(stream)
        }

        binding.listView.isClickable = true
        binding.listView.adapter = CustomAdapter(this, streamArrayList)
        binding.listView.setOnItemClickListener { parent, view, position, id ->
            val title = title[position]
            val detail = detail[position]
            val matkul = matkul[position]

            val i = Intent(this, StreamActivity::class.java)
            i.putExtra("title", title)
            i.putExtra("detail", detail)
            i.putExtra("matkul", matkul)
            startActivity(i)
        }

    }
}