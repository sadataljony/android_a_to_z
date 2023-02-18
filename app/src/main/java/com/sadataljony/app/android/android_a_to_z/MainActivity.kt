package com.sadataljony.app.android.android_a_to_z

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sadataljony.app.android.android_a_to_z.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDialogSuccess.setOnClickListener {
            this.showSuccessDialog(
                title = "Success",
                message = "This is a sample success dialog",
                buttonTitle = "OK"
            ) {
//            showToast()
                it.dismiss()
            }
        }

        binding.btnDialogFailure.setOnClickListener {
            this.showFailedDialog(
                title = "Failure",
                message = "This is a sample failure dialog",
                buttonTitle = "Cancel"
            ) {
                it.dismiss()
            }
        }
    }
}