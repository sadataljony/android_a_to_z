package com.sadataljony.app.android.android_a_to_z

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sadataljony.app.android.android_a_to_z.databinding.ActivityContactUsBinding

class ContactUsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickListener()
    }

    private fun clickListener() {
        binding.tvFindLocalCallersCall.setOnClickListener {
            openDialPad(binding.tvFindLocalCallers.text.toString())
        }
        binding.tvTollFree.setOnClickListener {
            openDialPad(binding.tvTollFreeTitle.text.toString())
        }
        binding.tvFindLocalAndOverseasCallersCall01.setOnClickListener {
            openDialPad(binding.tvForLocalAndOverseasCallers01.text.toString())
        }
        binding.tvFindLocalAndOverseasCallersCall02.setOnClickListener {
            openDialPad(binding.tvForLocalAndOverseasCallers02.text.toString())
        }
        binding.tvFindLocalAndOverseasCallersCall03.setOnClickListener {
            openDialPad(binding.tvForLocalAndOverseasCallers03.text.toString())
        }
        binding.tvMail.setOnClickListener {
            createEmailIntent(binding.tvMailAddress.text.toString())
        }
        binding.rlFaceBook.setOnClickListener {
            startActivity(getOpenFacebookIntent())
        }
        binding.rlInstagram.setOnClickListener {
            val uri = Uri.parse("https://www.instagram.com/bracbanklimited/?hl=en")
            val likeIng = Intent(Intent.ACTION_VIEW, uri)

            likeIng.setPackage("com.instagram.android")

            try {
                startActivity(likeIng)
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/bracbanklimited/?hl=en")
                    )
                )
            }
        }
        binding.rlLinkedIn.setOnClickListener {
            var intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://bd.linkedin.com/company/brac-bank-ltd")
            )
            val packageManager: PackageManager = this.packageManager
            val list =
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            if (list.isEmpty()) {
                intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://www.linkedin.com/profile/view?id=you")
                )
            }
            startActivity(intent)
        }
        binding.rlYouTube.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/channel/UCCw_DymPtA71cS6tisZePuw")
                )
            )
        }
        binding.rlBracBank.setOnClickListener {
            val uri = Uri.parse("https://www.bracbank.com/en/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    private fun getOpenFacebookIntent(): Intent? {
        return try {
            packageManager.getPackageInfo("com.facebook.katana", 0)
            Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/164557134849"))
//            Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/bracbank"))
        } catch (e: java.lang.Exception) {
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com"))
//            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/appetizerandroid"))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun openDialPad(mobileNo: String?) {
        try {
            val call = Uri.parse("tel:$mobileNo")
            val surf = Intent(Intent.ACTION_DIAL, call)
            startActivity(surf)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createEmailIntent(mail: String?) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:$mail"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "")
            intent.putExtra(Intent.EXTRA_TEXT, "")
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}