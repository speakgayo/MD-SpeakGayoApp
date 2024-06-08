package com.khairililmi.speakgayo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.khairililmi.speakgayo.databinding.ActivityMainBinding
import com.khairililmi.speakgayo.ui.favorite.FavoriteFragment
import com.khairililmi.speakgayo.ui.history.HistoryFragment
import com.khairililmi.speakgayo.ui.home.HomeFragment
import com.khairililmi.speakgayo.ui.news.NewsFragment
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var textRecognizer: TextRecognizer? = null
    private var imageUri: Uri? = null
    private val REQUEST_CODE_PICK_IMAGE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToHomeFragment()

        binding.navView.background = null

        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        binding.cameraNavigation.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1060)
                .start(REQUEST_CODE_PICK_IMAGE)
        }

        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> replaceFragment(HomeFragment())
                R.id.navigation_news -> replaceFragment(NewsFragment())
                R.id.navigation_favorite -> replaceFragment(FavoriteFragment())
                R.id.navigation_history -> replaceFragment(HistoryFragment())
            }
            true
        }
    }
    @Deprecated("This method is deprecated. Please use ActivityResultLauncher instead.", ReplaceWith("ActivityResultLauncher"))
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    imageUri = data.data
                    Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show()
                    recognizeText()
                }
            } else {
                Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun recognizeText() {
        imageUri?.let {
            try {
                val inputImage = InputImage.fromFilePath(this, it)
                val options = TextRecognizerOptions.DEFAULT_OPTIONS
                val textRecognizer = TextRecognition.getClient(options)
                textRecognizer.process(inputImage)
                    .addOnSuccessListener { text ->
                        val recognizedText = text.text
                        val homeFragment = supportFragmentManager.findFragmentById(R.id.frame_layout) as HomeFragment?
                        homeFragment?.setText(recognizedText)
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to recognize text: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToHomeFragment() {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, HomeFragment())
        fragmentTransaction.commit()
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}
