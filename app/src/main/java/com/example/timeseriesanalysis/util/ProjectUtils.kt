package com.example.timeseriesanalysis.util

import android.app.ProgressDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import com.example.timeseriesanalysis.R

class ProjectUtils(val mContext:Context) {

    var progressDialog: AlertDialog?=null

    //show a progress dialog using alert dialog
    fun showProjectDialog(message:String){
        if(progressDialog!=null){
            progressDialog?.dismiss()
        }
        try {
            var progressDialogBuilder= AlertDialog.Builder(mContext)
            var view= LayoutInflater.from(mContext).inflate(R.layout.alert_box_progress_dialog,null)
            var tvMessage=view.findViewById<AppCompatTextView>(R.id.tvMessage)
            tvMessage.text="$message"
            progressDialogBuilder.setCancelable(false)
            progressDialogBuilder.setView(view)
            progressDialog = progressDialogBuilder.create()
            progressDialog!!.show()
        } catch (e: Exception) {
        }
    }

    //hide progress dialog
    fun hideProgressDialog(){
        progressDialog?.dismiss()
    }

    companion object{
        var mProjectUtils:ProjectUtils?=null
        fun getInstance(context: Context):ProjectUtils{
            if(mProjectUtils==null)
                mProjectUtils=ProjectUtils(context)

            return mProjectUtils!!
        }
    }

}