package com.sadataljony.app.android.android_a_to_z

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.sadataljony.app.android.android_a_to_z.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etDateOfBirth.setOnTouchListener { v, event ->
            if (event.action === MotionEvent.ACTION_DOWN) {
                TimeAndDatePicker.datePicker(
                    this@MainActivity, "yyyy-MM-dd", binding.etDateOfBirth,
                    Calendar.getInstance().timeInMillis, 0
                )
            }
            false
        }
    }
}