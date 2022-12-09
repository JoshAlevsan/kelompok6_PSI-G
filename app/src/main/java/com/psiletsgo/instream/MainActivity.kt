package com.psiletsgo.instream

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        btn_ksn.setOnClickListener() {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_stm.setOnClickListener() {
            val intent = Intent(this, StreamListActivity::class.java)
            startActivity(intent)
        }

        btn_pcb.setOnClickListener() {
            val quiz = Kuesioner(this)
            val question = Pertanyaan(
                this, null, null, null, -1, "null", null
            )
            val toast = Toast.makeText(
                this,
                "Jumlah Percobaan Sebelumnya: "
                    + quiz.countTries().toString(),
                Toast.LENGTH_SHORT
            )
            toast.show()
            question.resetPertanyaan()
            quiz.emptyAll()
        }
    }
}