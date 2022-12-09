package com.psiletsgo.instream

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class Pertanyaan(
    context: Context?, id: Int?, q_id: Int?,
    no_stream: Int?, nomor: Int, isi: String, respon: String?
    ):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private var context: Context? = null
    private var id: Int? = null
    private var q_id: Int? = null
    private var no_stream: Int? = null
    private var nomor: Int? = null
    private var isi: String? = null
    private var respon: String? = null

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "instream.db"
        private const val TBL_PERTANYAAN = "pertanyaan"
        private const val ID = "id"
        private const val Q_ID = "q_id"
        private const val NOMOR = "nomor"
        private const val NO_STREAM= "no_stream"
        private const val ISI = "isi"
        private const val RESPON = "respon"
    }

    init {
        val db = this.writableDatabase

        this.context = context
        this.id = id
        this.q_id = q_id
        this.no_stream = no_stream
        this.nomor = nomor
        this.isi = isi
        this.respon = respon

        onCreate(db)
        addPertanyaan()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createPertanyaan = (
                "CREATE TABLE IF NOT EXISTS $TBL_PERTANYAAN ("
                        + "$ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "$Q_ID INTEGER,"
                        + "$NO_STREAM INTEGER,"
                        + "$NOMOR INTEGER,"
                        + "$ISI TEXT,"
                        + "$RESPON TEXT"
                        + ")"
                )
        db?.execSQL(createPertanyaan)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_PERTANYAAN")
    }

    fun addPertanyaan() {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, this.id)
        contentValues.put(Q_ID, this.q_id)
        contentValues.put(NO_STREAM, this.no_stream)
        contentValues.put(NOMOR, this.nomor)
        contentValues.put(ISI, this.isi)
        contentValues.put(RESPON, this.respon)

        db.insert(TBL_PERTANYAAN, null, contentValues)

    }

    fun resetPertanyaan() {
        val db = this.writableDatabase

        db.delete(
            TBL_PERTANYAAN,
            null, null
        )
    }

    fun takeQuestion(q_id: Int, nomor: Int): Pertanyaan? {
        val db = readableDatabase
        val columns = arrayOf(ID, Q_ID, NO_STREAM, NOMOR, ISI, RESPON)
        val select: Cursor = db.query(
            TBL_PERTANYAAN, columns,
            "$Q_ID = ? AND $NOMOR = ?",
            arrayOf("$q_id", "$nomor"),
            null, null, null
        )

        val index = arrayOf(
            select.getColumnIndex(ID),
            select.getColumnIndex(Q_ID),
            select.getColumnIndex(NO_STREAM),
            select.getColumnIndex(NOMOR),
            select.getColumnIndex(ISI),
            select.getColumnIndex(RESPON)
        )

        var pertanyaan: Pertanyaan? = null
        if(select.moveToFirst())
            do {
                pertanyaan = Pertanyaan(
                    this.context,
                    select.getInt(index[0]),
                    select.getInt(index[1]),
                    select.getInt(index[2]),
                    select.getInt(index[3]),
                    select.getString(index[4]),
                    select.getString(index[5]),
                )
            } while(!select.isAfterLast)
        return pertanyaan
    }

    fun showQuestion(): String? {
        return this.isi
    }

    fun storeAnswer(respon: String?) {
        val db = writableDatabase
        this.respon = respon
        val contentValues = ContentValues()
        contentValues.put(RESPON, this.respon)

        val q_id = this.q_id
        val nomor = this.nomor
        db.update(
            TBL_PERTANYAAN, contentValues,
            "$Q_ID = ? AND $NOMOR = ?",
            arrayOf("$q_id", "$nomor")
        )
    }

    fun takeAnswer(q_id: Int?, nomor: Int): String {
        val db = readableDatabase

        val column = arrayOf(RESPON, NO_STREAM)
        val select: Cursor = db.query(
            TBL_PERTANYAAN, column,
            "$Q_ID = ? AND $NOMOR = ?",
            arrayOf("$q_id", "$nomor"),
            null, null, null
        )
        val index = arrayOf(
            select.getColumnIndex(RESPON),
            select.getColumnIndex(NO_STREAM)
        )
        var answer = ""
        if(select.moveToFirst()) {
            answer = select.getString(index[0]) + "+" + "${select.getString(index[1])}"
        }
        return answer
    }
}