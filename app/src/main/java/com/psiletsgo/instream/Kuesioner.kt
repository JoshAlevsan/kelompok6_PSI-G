package com.psiletsgo.instream

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class Kuesioner(context: Context?):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private var context: Context? = null
    private var id: Int? = null
    private var questions: ArrayList<Pertanyaan>? = ArrayList()

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "instream.db"
        private const val TBL_KUESIONER = "kuesioner"
        private const val ID = "id"
    }

    init {
        this.writableDatabase

        this.context = context
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createKuesioner = (
                "CREATE TABLE IF NOT EXISTS $TBL_KUESIONER ("
                + "$ID INTEGER PRIMARY KEY AUTOINCREMENT"
                + ")"
                )
        db?.execSQL(createKuesioner)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_KUESIONER")
    }

    fun addKuesioner() {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, this.id)

        db.insert(TBL_KUESIONER, null, contentValues)
        val select: Cursor = db.query(
            TBL_KUESIONER, arrayOf(ID),
            null, null,
            null, null, null
        )

        if(select.moveToLast()) {
            val index = select.getColumnIndex(ID)
            this.id = select.getInt(index)
        }
        loadQuestions()
    }

    fun loadQuestions() {
        val storeId = this.id
        val isi = ArrayList<String>()
        isi.add("Apakah kamu tertarik dengan hal-hal yang berbau statistika?")
        isi.add("Apakah kamu sering memakai bahasa pemrograman Python?")
        isi.add("Apakah kamu tertarik mengenai mekanisme kecerdasan buatan?")
        isi.add("Apakah kamu tertarik mengenai algoritma dalam pemrograman untuk menyelesaikan" +
                "masalah secara otomatis?")
        isi.add("Apakah kamu tertarik dengan jaringan antar-komputer yang ada di seluruh dunia?")
        isi.add("Apakah kamu tertarik mengenai proses penyimpanan data pada server?")
        isi.add("Apakah kamu sering memakai Sistem Operasi Kali Linux?")
        isi.add("Apakah kamu tertarik mendalami teknik-teknik hacking dan keamanan" +
                "sistem jaringan?")
        isi.add("Apakah kamu tertarik dalam proses pembuatan sistem website?")
        isi.add("Apakah kamu suka dalam melakukan manajemen proyek?")
        isi.add("Apakah kamu tertarik dengan proses pembuatan game?")
        isi.add("Apakah kamu tertarik dengan hal-hal yang berbau statistika?")
        isi.add("Apakah kamu tertarik dengan proses pembuatan aplikasi android?")

        this.questions?.add(
            Pertanyaan(this.context, null, storeId, 1, 1,
                isi[0], null)
        )
        this.questions?.add(
            Pertanyaan(this.context, null, storeId, 1, 2,
                isi[1], null)
        )
        this.questions?.add(
            Pertanyaan(this.context, null, storeId, 2, 3,
                isi[2], null)
        )
        this.questions?.add(
            Pertanyaan(this.context, null, this.id, 2, 4,
                isi[3], null)
        )
        this.questions?.add(
            Pertanyaan(this.context, null, this.id, 3, 5,
                isi[4], null)
        )
        this.questions?.add(
            Pertanyaan(this.context, null, this.id, 3, 6,
                isi[5], null)
        )
        this.questions?.add(
            Pertanyaan(this.context, null, this.id, 4, 7,
                isi[6], null)
        )
        this.questions?.add(
            Pertanyaan(this.context, null, this.id, 4, 8,
                isi[7], null)
        )
        this.questions?.add(
            Pertanyaan(this.context, null, this.id, 5, 9,
                isi[8], null)
        )
        this.questions?.add(
            Pertanyaan(this.context, null, this.id, 5, 10,
                isi[9], null)
        )
        this.questions?.add(
            Pertanyaan(this.context, null, this.id, 6, 11,
                isi[10], null)
        )
        this.questions?.add(
            Pertanyaan(this.context, null, this.id, 6, 12,
                isi[11], null)
        )
    }

    fun takeQuestionsList(): ArrayList<Pertanyaan>? {
        val pertanyaan: Pertanyaan? = null
        for(i in 1..12) {
            this.id?.let {
                pertanyaan?.takeQuestion(it, i)?.let {
                    this.questions?.add(it)
                }
            }
        }
        return this.questions
    }

    fun countTries(): Int {
        val db = this.readableDatabase

        val select: Cursor = db.query(
            TBL_KUESIONER, arrayOf(ID),
            null, null,
            null, null, null
        )

        return select.count
    }

    fun emptyAll() {
        val db = this.writableDatabase

        db.delete(
            TBL_KUESIONER,
            null, null
        )
    }

    fun getId(): Int? {
        return this.id
    }

    fun calculateRecomendation(question: Pertanyaan, k_id: Int?): ArrayList<Int> {
        val jawaban = ArrayList<String>()
        val no_stream = ArrayList<Int>()
        for(i in 1..12) {
            var fullString = question.takeAnswer(k_id, i)
            var pisah = fullString.split("+")
            jawaban.add(pisah[0])
            no_stream.add(pisah[1].toInt())
        }

        var rekomendasi: ArrayList<Int> = arrayListOf(0, 0, 0, 0, 0, 0)
        for(i in 0..11) {
            if (jawaban[i] == "Ya") {
                var index = no_stream[i] - 1
                rekomendasi[index]++
            }
        }
        return rekomendasi
    }
}