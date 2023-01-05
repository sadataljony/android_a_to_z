package com.sadataljony.app.android.android_a_to_z

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sadataljony.app.android.android_a_to_z.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetDirection.setOnClickListener {
            if (binding.etLat01.text.trim().toString().isEmpty()) {
                binding.etLat01.requestFocus()
                binding.etLat01.error = "Latitude required"
            } else if (binding.etLong01.text.trim().toString().isEmpty()) {
                binding.etLong01.requestFocus()
                binding.etLong01.error = "Longitude required"
            } else if (binding.etLat02.text.trim().toString().isEmpty()) {
                binding.etLat02.requestFocus()
                binding.etLat02.error = "Latitude required"
            } else if (binding.etLong02.text.trim().toString().isEmpty()) {
                binding.etLong02.requestFocus()
                binding.etLong02.error = "Longitude required"
            } else {
                val lat01 = binding.etLat01.text.trim().toString().toDouble()
                val long01 = binding.etLong01.text.trim().toString().toDouble()
                val lat02 = binding.etLat02.text.trim().toString().toDouble()
                val long02 = binding.etLong02.text.trim().toString().toDouble()
                //Show Point
//                val uri = Uri.parse("geo:$lat01,$long01")
                //Show direction from current location
//                val uri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$lat01,$long01")
                //Show direction from Location 1 to Location 2
                val uri =
                    Uri.parse("https://www.google.com/maps/dir/?api=1&origin=$lat01,$long01&destination=$lat02,$long02")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.google.android.apps.maps")
                startActivity(intent)
            }
        }
    }
}