package com.psiletsgo.instream

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.activity_rec.*


class RecActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rec)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        val data = intent.getIntegerArrayListExtra("rec")
        Log.d("MsgShowIntent", "$data")

        if (data != null) {
            showBarChart(data)
        }

        btn_hm.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_stm.setOnClickListener {
            val intent = Intent(this, StreamListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun showBarChart(data: ArrayList<Int>) {
        var dataVals = ArrayList<BarEntry>()
        for(i in 0..5) {
            dataVals.add(BarEntry(i.toFloat() + 1, data[i].toFloat() * 50))
        }

        val barDataSet = BarDataSet(dataVals, "Dataset 1")
        val barData = BarData()
        barData.addDataSet(barDataSet)
        bar_chart.data = barData
        bar_chart.invalidate()

        initBarChart()
        initBarDataSet(barDataSet)
    }

    private fun initBarDataSet(barDataSet: BarDataSet) {
        //Changing the color of the bar
        barDataSet.color = Color.parseColor("#304567")
        //Setting the size of the form in the legend
        barDataSet.formSize = 15f
        //showing the value of the bar, default true if not set
        barDataSet.setDrawValues(false)
        //setting the text size of the value of the bar
        barDataSet.valueTextSize = 12f
    }

    private fun initBarChart() {
        //hiding the grey background of the chart, default false if not set
        bar_chart.setDrawGridBackground(false)
        //remove the bar shadow, default false if not set
        bar_chart.setDrawBarShadow(false)
        //remove border of the chart, default false if not set
        bar_chart.setDrawBorders(false)

        //remove the description label text located at the lower right corner
        val description = Description()
        description.setEnabled(false)
        bar_chart.setDescription(description)

        //setting animation for y-axis, the bar will pop up from 0 to its value within the time we set
        bar_chart.animateY(1000)
        //setting animation for x-axis, the bar will pop up separately within the time we set
        bar_chart.animateX(1000)
        val xAxis: XAxis = bar_chart.getXAxis()

        //change the position of x-axis to the bottom
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        //set the horizontal distance of the grid line
        xAxis.granularity = 1f
        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(false)
        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(false)

        //hiding the left y-axis line, default true if not set
        val leftAxis: YAxis = bar_chart.getAxisLeft()
        leftAxis.setDrawAxisLine(false)
        //hiding the right y-axis line, default true if not set
        val rightAxis: YAxis = bar_chart.getAxisRight()
        rightAxis.setDrawAxisLine(false)

        val legend: Legend = bar_chart.getLegend()
        //setting the shape of the legend form to line, default square shape
        legend.isEnabled = false
    }


}