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

    }
    fun positiveAction(action:() -> Unit){
        dlgAlert.setPositiveButton("Ок",
            DialogInterface.OnClickListener { dialog, which ->
                action()
            })
    }
    fun negativeAction(action:() -> Unit){
        dlgAlert.setNegativeButton("Отмена",
            DialogInterface.OnClickListener { dialog, which ->
                action()
            })
    }
    fun show(){
        dlgAlert.create()
        dlgAlert.show()
    }
}