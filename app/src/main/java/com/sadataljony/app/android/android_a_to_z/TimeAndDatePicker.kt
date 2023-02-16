package com.sadataljony.app.android.android_a_to_z

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

object TimeAndDatePicker {
    var date: Calendar? = null
    var time: Calendar? = null

    fun dateAndTimePicker(
        context: Context?, pattern: String?,
        textView: TextView, maxLimit: Long, minLimit: Long
    ) {
        val currentDate = Calendar.getInstance()
        date = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            (context)!!, R.style.DialogTheme,
            { _, year, monthOfYear, dayOfMonth ->
                date.let {
                    it?.set(year, monthOfYear, dayOfMonth)
                    textView.text = SimpleDateFormat(pattern).format(it?.time) + ""
                }

            }, currentDate[Calendar.YEAR], currentDate[Calendar.MONTH],
            currentDate[Calendar.DATE]
        )
        if (maxLimit != 0L) datePickerDialog.datePicker.maxDate = maxLimit - 1000
        if (minLimit != 0L) datePickerDialog.datePicker.minDate = minLimit
        datePickerDialog.show()
    }

    fun datePicker(
        context: Context?, pattern: String?, textView: TextView,
        maxLimit: Long, minLimit: Long
    ) {
        val currentDate = Calendar.getInstance()
        date = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            (context)!!,
            { _, year, monthOfYear, dayOfMonth ->
                date.let {
                    it?.set(year, monthOfYear, dayOfMonth)
                    textView.text = SimpleDateFormat(pattern).format(it?.time) + ""
                }
            }, currentDate[Calendar.YEAR], currentDate[Calendar.MONTH],
            currentDate[Calendar.DATE]
        )
        if (maxLimit != 0L) datePickerDialog.datePicker.maxDate = maxLimit - 1000
        if (minLimit != 0L) datePickerDialog.datePicker.minDate = minLimit
        datePickerDialog.show()
    }

    fun timePicker(context: Context?, textView: TextView, title: String?) {
        val currentTime = Calendar.getInstance()
        time = Calendar.getInstance()
        val hour = currentTime[Calendar.HOUR_OF_DAY]
        val minute = currentTime[Calendar.MINUTE]
        val mTimePicker: TimePickerDialog = TimePickerDialog(
            context, R.style.DialogTheme,
            { _, selectedHour, selectedMinute ->
                time.let {
                    it?.set(Calendar.HOUR_OF_DAY, selectedHour)
                    it?.set(Calendar.MINUTE, selectedMinute)
                    textView.text = SimpleDateFormat("hh:mm a").format(it?.time) + ""
                }
            }, hour, minute, false
        ) //Yes 24 hour time
        mTimePicker.setTitle(title)
        mTimePicker.show()
    }

    fun timePicker(
        context: Context?, textView: TextView, is24Hour: Boolean,
        title: String?
    ) {
        val currentTime = Calendar.getInstance()
        val pattern = if (is24Hour) "HH:mm" else "hh:mm a"
        time = Calendar.getInstance()
        val hour = currentTime[Calendar.HOUR_OF_DAY]
        val minute = currentTime[Calendar.MINUTE]
        val mTimePicker: TimePickerDialog = TimePickerDialog(
            context, R.style.DialogTheme,
            { _, selectedHour, selectedMinute ->
                time.let {
                    it?.set(Calendar.HOUR_OF_DAY, selectedHour)
                    it?.set(Calendar.MINUTE, selectedMinute)
                    textView.text = SimpleDateFormat(pattern).format(it?.time) + ""
                }
            }, hour, minute, is24Hour
        )
        mTimePicker.setTitle(title)
        mTimePicker.show()
    }
}