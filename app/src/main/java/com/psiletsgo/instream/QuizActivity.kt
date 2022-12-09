package com.psiletsgo.instream

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity(), View.OnClickListener {
    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Pertanyaan>? = null
    private var mSelectedOptionPosition: Int = 0
    private var tempAns: String? = null
    private var storedId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        val kuesioner = Kuesioner(this)
        kuesioner.addKuesioner()
        storedId = kuesioner.getId()
        mQuestionsList = kuesioner.takeQuestionsList()
        setQuestion()

        tv_yes.setOnClickListener(this)
        tv_no.setOnClickListener(this)
        btn_back.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
    }

    private fun setQuestion() {
        defaultOptionsView()
        tempAns = null
        val question = mQuestionsList!![mCurrentPosition - 1]
        pgr_bar.progress = mCurrentPosition
        tv_qst.text = question.showQuestion()
        tv_pgr.text = "$mCurrentPosition" + "/" + "${pgr_bar.max}"
    }

    private fun defaultOptionsView() {
        if(mCurrentPosition == 12) {
            btn_submit.text = "Submit"
        } else {
            btn_submit.text = "Next"
        }
        val options = ArrayList<TextView>()
        options.add(0, tv_yes)
        options.add(1, tv_no)

        for(option in options) {
            option.setTextColor(Color.parseColor("#676968"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this, R.drawable.default_option_border_bg
            )
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_yes -> {
                selectedOptionView(tv_yes, 1)
            }
            R.id.tv_no -> {
                selectedOptionView(tv_no, 2)
            }
            R.id.btn_back -> {
                Log.d("MsgShow", "Back")
                if(mCurrentPosition > 1) {
                    mCurrentPosition--
                    setQuestion()
                }
            }
            R.id.btn_submit -> {
                val question = mQuestionsList!![mCurrentPosition - 1]
                if(tempAns != null) {
                    question.storeAnswer(tempAns)
                    when {
                        mCurrentPosition < mQuestionsList!!.size -> {
                            mCurrentPosition++
                            setQuestion()
                        } else -> {
                        val kuesioner = Kuesioner(this)
                        val test = kuesioner.calculateRecomendation(question, storedId)
                        val intent = Intent(this, RecActivity::class.java)
                            .putExtra("rec", test)
                        startActivity(intent)
                        finish()
                        }
                    }
                } else {
                    val toast = Toast.makeText(
                        this,
                        "Pilihan Belum Ada!",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                }
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#E19201"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this, R.drawable.selected_option_border_bg
        )
        tempAns = tv.text.toString()
    }
}