package com.sadataljony.app.android.android_a_to_z

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.RESULT_ERROR
import com.sadataljony.app.android.android_a_to_z.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val READ_EXTERNAL_CODE = 1002
    private val CAMERA_PERMISSION_CODE = 1001
    private val FILE_SELECT_CODE: Int = 9001

    private val IMAGE = "image/*"
    private val PDF = "application/pdf"

    private var fileType = ""
    private lateinit var extension: String
    private var fileBase64: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            requestReadPermission()
        }
    }

    private fun requestReadPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) === PackageManager.PERMISSION_GRANTED) {
                requestForCameraPermission()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_EXTERNAL_CODE
                )
            }
        }
    }

    private fun requestForCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.CAMERA) === PackageManager.PERMISSION_GRANTED) {
                // openChooseDialog();// openCamera();
                showFilePickerDialog()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
        }
    }

    private fun showFilePickerDialog() {
        val messageBoxView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_file_chooser, null as ViewGroup?)
        val messageBoxBuilder = AlertDialog.Builder(this).setView(messageBoxView)
        val messageBoxInstance = messageBoxBuilder.show()
        val btnCancel = messageBoxView.findViewById<View>(R.id.btnCancel) as Button
        val imgPhoto = messageBoxView.findViewById<View>(R.id.imgPhoto) as ImageView
        val imgPdf = messageBoxView.findViewById<View>(R.id.imgPdf) as ImageView
        imgPhoto.setOnClickListener {
            openChooseDialog()
            messageBoxInstance.cancel()
            fileType = IMAGE
        }
        imgPdf.setOnClickListener {
            openFilePicker()
            messageBoxInstance.cancel()
            fileType = PDF
        }
        btnCancel.setOnClickListener {
            messageBoxInstance.cancel()
        }
    }

    private fun openChooseDialog() {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start()
    }

    private fun openFilePicker() {
        val intent = getCustomFileChooserIntent(PDF)
        try {
            startActivityForResult(
                Intent.createChooser(intent, "Select a File to Upload"),
                FILE_SELECT_CODE
            )
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                try {
                    val fileUri = data?.data
                    val file = File(fileUri?.path)
                    binding.apply {
                        documentPreviewLayout.visibility = View.VISIBLE
                        txtFileName.text = file.name
                    }
                    if (fileType == PDF) {
                        extension = "PDF"
                    } else {
                        extension = getExtension(file.name)
                    }
                    if (requestCode == FILE_SELECT_CODE) {
                        val iStream = this.contentResolver.openInputStream(
                            fileUri!!
                        )
                        val inputData = getBytes(iStream)
                        val fileSizeInBytes = inputData.size.toLong()
                        val fileSizeInKB = fileSizeInBytes / 1024
                        val fileSizeInMB = fileSizeInKB / 1024
                        if (fileSizeInMB > 5) {
                            Toast.makeText(
                                this,
                                "Your document is too big, Please upload a file that's smaller than 5MB.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            fileBase64 = Base64.encodeToString(inputData, Base64.DEFAULT)
                        }
                    } else {
                        val bitmap =
                            MediaStore.Images.Media.getBitmap(
                                this.contentResolver,
                                fileUri
                            )
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                        binding.imgDocumentPreview.setImageBitmap(bitmap)
                        val byteArray = stream.toByteArray()
                        fileBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT)
                        extension = "PNG"
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            RESULT_ERROR -> {
                Toast.makeText(
                    this,
                    "An error occurred when fetched document, please try again later.",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {
            }
        }
    }

    @Throws(IOException::class)
    fun getBytes(inputStream: InputStream?): ByteArray {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        while (inputStream!!.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }

    private fun getExtension(fileName: String): String {
        val encoded: String = try {
            URLEncoder.encode(fileName, "UTF-8").replace("+", "%20")
        } catch (e: Exception) {
            fileName
        }
        return MimeTypeMap.getFileExtensionFromUrl(encoded).uppercase()
    }

    private fun getCustomFileChooserIntent(vararg types: String?): Intent {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_MIME_TYPES, types)
        return intent
    }

}