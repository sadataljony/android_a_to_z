package com.sadataljony.app.android.android_a_to_z

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sadataljony.app.android.android_a_to_z.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            Toast.makeText(
                this@MainActivity,
                "Floating Action Button Working",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}