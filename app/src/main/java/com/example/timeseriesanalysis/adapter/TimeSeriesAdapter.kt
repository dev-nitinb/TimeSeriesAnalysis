package com.example.timeseriesanalysis.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.timeseriesanalysis.R
import com.example.timeseriesanalysis.model.TimeSeriesModel

class TimeSeriesAdapter(private val mContext:Context, private val alTimeSeries:ArrayList<TimeSeriesModel>):RecyclerView.Adapter<TimeSeriesAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(mContext).inflate(R.layout.items_time_series,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return alTimeSeries.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(alTimeSeries[holder.adapterPosition])
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDate: AppCompatTextView =itemView.findViewById(R.id.tvDate) as AppCompatTextView
        private val tvOpen: AppCompatTextView =itemView.findViewById(R.id.tvOpen) as AppCompatTextView
        private val tvHigh: AppCompatTextView =itemView.findViewById(R.id.tvHigh) as AppCompatTextView
        private val tvLow: AppCompatTextView =itemView.findViewById(R.id.tvLow) as AppCompatTextView
        private val tvClose: AppCompatTextView =itemView.findViewById(R.id.tvClose) as AppCompatTextView

        fun bindData(timeSeriesModel: TimeSeriesModel){
            //add separate color to textview
            tvDate.text="${timeSeriesModel.date}"
            tvOpen.text=HtmlCompat.fromHtml("<font color='#01C6DB'>Open:</font> ${timeSeriesModel.open}",HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvHigh.text=HtmlCompat.fromHtml("<font color='#01C6DB'>High:</font> ${timeSeriesModel.high}",HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvLow.text=HtmlCompat.fromHtml("<font color='#01C6DB'>Low:</font> ${timeSeriesModel.low}",HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvClose.text=HtmlCompat.fromHtml("<font color='#01C6DB'>Close:</font> ${timeSeriesModel.close}",HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }

}