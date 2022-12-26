package com.sadataljony.app.android.android_a_to_z

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AlertDialog


class ViewExt {
}

fun Context.showSuccessDialog(
    title: String,
    message: String,
    buttonTitle: String? = "Ok",
    callback: ((DialogInterface) -> Unit?)? = null
) {
    val alertDialog: AlertDialog = AlertDialog.Builder(this)
        .setTitle(title)
//            .setIcon(R.drawable.ic_exit)
        .setMessage(message)
        .setPositiveButton(
            buttonTitle
        ) { dialog: DialogInterface, cancel: Int ->
            callback?.let {
                callback.invoke(dialog)
            }
            dialog.dismiss()
        }
        .create()
    alertDialog.setOnShowListener { arg0: DialogInterface? ->
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(Color.GREEN)
    }
    alertDialog.show()
}

fun Context.showFailedDialog(
    title: String,
    message: String?,
    buttonTitle: String? = "Cancel",
    callback: ((DialogInterface) -> Unit?)? = null
) {
    val alertDialog: AlertDialog = AlertDialog.Builder(this)
        .setTitle(title)
//            .setIcon(R.drawable.ic_exit)
        .setMessage(message ?: "")
        .setNegativeButton(
            buttonTitle
        ) { dialog: DialogInterface, cancel: Int ->
            callback?.let {
                callback.invoke(dialog)
            } ?: kotlin.run {
                dialog.dismiss()
            }
        }
        .create()
    alertDialog.setOnShowListener {
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(Color.RED)
    }
    alertDialog.show()
}

