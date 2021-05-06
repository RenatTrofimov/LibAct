package com.example.libact

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class DialogMessage(context: Context,
                    title:String,
                    message: String){
    private val dlgAlert: AlertDialog.Builder = AlertDialog.Builder(context)
    init{
        dlgAlert.setMessage(message)
        dlgAlert.setTitle(title)
        dlgAlert.setCancelable(true)
        dlgAlert.setPositiveButton("Ок",
            DialogInterface.OnClickListener { dialog, which ->
                //dismiss the dialog
            })
        dlgAlert.create()
    }
    fun show(){
        dlgAlert.show()
    }
}