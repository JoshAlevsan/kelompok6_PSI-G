package com.psiletsgo.instream

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomAdapter(private val context : Activity, private val arrayList : ArrayList<Stream>) : ArrayAdapter<Stream>(context, R.layout.list_stream, arrayList) {
    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_stream, null)

        val title : TextView = view.findViewById(R.id.title)
        val info : TextView = view.findViewById(R.id.info)

        title.text = arrayList[position].title
        info.text = arrayList[position].info

        return view
    }
}