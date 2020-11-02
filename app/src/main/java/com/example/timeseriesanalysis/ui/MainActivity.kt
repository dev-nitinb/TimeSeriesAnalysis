package com.example.timeseriesanalysis.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timeseriesanalysis.R
import com.example.timeseriesanalysis.adapter.TimeSeriesAdapter
import com.example.timeseriesanalysis.model.TimeSeriesModel
import com.example.timeseriesanalysis.util.ProjectUtils
import com.example.timeseriesanalysis.viewModel.TimeSeriesViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

const val TAG="MainActivity"
class MainActivity : AppCompatActivity() {

    lateinit var rvTimeSeries:RecyclerView
    lateinit var timeSeriesAdapter: TimeSeriesAdapter
    lateinit var alTimeSeries:ArrayList<TimeSeriesModel>
    lateinit var mProjectUtils:ProjectUtils
    lateinit var pieChart: PieChart
    lateinit var timeSeriesEntry:ArrayList<PieEntry>

    //calculate total
    var openTotal=0.0f
    var highTotal=0.0f
    var lowTotal=0.0f
    var closeTotal=0.0f

    //view model
    lateinit var timeSeriesViewModel: TimeSeriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindView()

        timeSeriesViewModel.fetchTimeSeriesData(this@MainActivity).observe(this, Observer {
            //data change notified
            alTimeSeries.addAll(it)
            timeSeriesAdapter.notifyDataSetChanged()
            mProjectUtils.hideProgressDialog()
            Log.e(TAG, alTimeSeries.toString())
            for(i in 0 until alTimeSeries.size-1){
                openTotal+=alTimeSeries[i].open.toFloat()
                highTotal+=alTimeSeries[i].high.toFloat()
                lowTotal+=alTimeSeries[i].low.toFloat()
                closeTotal+=alTimeSeries[i].close.toFloat()
            }
            viewPieChart()
        })

    }

    //initialize view and variable
    private fun bindView(){
        pieChart=findViewById(R.id.pieChart)
        rvTimeSeries= findViewById<RecyclerView>(R.id.rvTimeSeries)

        alTimeSeries= ArrayList()
        timeSeriesViewModel= ViewModelProvider(this).get(TimeSeriesViewModel::class.java)

        timeSeriesEntry=ArrayList()
        timeSeriesAdapter=TimeSeriesAdapter(this,alTimeSeries)
        rvTimeSeries.layoutManager=LinearLayoutManager(this)
        rvTimeSeries.adapter=timeSeriesAdapter

        //static objects
        mProjectUtils=ProjectUtils.getInstance(this)
    }

    //display picahrt
    private fun viewPieChart(){
        pieChart.visibility=View.VISIBLE
        timeSeriesEntry.clear()

        //entries
        timeSeriesEntry.add(PieEntry(openTotal, "Open"))
        timeSeriesEntry.add(PieEntry(highTotal, "High"))
        timeSeriesEntry.add(PieEntry(lowTotal, "Low"))
        timeSeriesEntry.add(PieEntry(closeTotal, "Close"))

        //set random color
        val colors: ArrayList<Int> = ArrayList()

        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)

        //decorate pie chart
        val pieDataSet = PieDataSet(timeSeriesEntry, "Time Series Daily")
        pieDataSet.setDrawIcons(false)
        pieDataSet.colors = colors
        pieDataSet.sliceSpace = 3f
        pieDataSet.selectionShift = 5f
        pieDataSet.valueTextSize=15f
        pieDataSet.valueTextColor=Color.BLACK

        val pieData=PieData(pieDataSet)
        //basic properties
        pieChart.data=pieData
        pieChart.description.isEnabled=false
        pieChart.highlightValues(null)
        pieChart.centerText="Time Series Daily"
    }
}