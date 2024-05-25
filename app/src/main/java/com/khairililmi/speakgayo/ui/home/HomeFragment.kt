package com.khairililmi.speakgayo.ui.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
<<<<<<< HEAD
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
=======
import android.os.Bundle
>>>>>>> 8d13816 (second)
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
<<<<<<< HEAD
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
=======
>>>>>>> 8d13816 (second)
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.khairililmi.speakgayo.data.local.favorite.FavoriteEntity
import com.khairililmi.speakgayo.data.local.history.AppHistoryDb
import com.khairililmi.speakgayo.data.local.history.HistoryEntity
import com.khairililmi.speakgayo.databinding.FragmentHomeBinding
import com.khairililmi.speakgayo.ui.favorite.AppFavoriteDb
<<<<<<< HEAD
import com.khairililmi.speakgayo.ui.home.speech.SpeechToTextConverter
import com.khairililmi.speakgayo.ui.home.speech.onRecognitionListener

class HomeFragment : Fragment(), onRecognitionListener {
=======

class HomeFragment : Fragment() {
>>>>>>> 8d13816 (second)

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: TranslateViewModel
    private var fromLangText = "Indonesia"
    private var toLangText = "Gayo"
<<<<<<< HEAD
    private lateinit var speechToTextConverter: SpeechToTextConverter
=======
>>>>>>> 8d13816 (second)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favoriteDao = AppFavoriteDb.getDatabase(requireContext()).favoriteDao()
        val historyDao = AppHistoryDb.getDatabase(requireContext()).historyDao()
        val repository = TranslateRepository(favoriteDao,historyDao)
        viewModel = ViewModelProvider(this, TranslateViewModelFactory(repository)).get(TranslateViewModel::class.java)

        binding.buttonTranslate.setOnClickListener {
            val textToTranslate = binding.editTextInput.text.toString()
            if (textToTranslate.isNotEmpty()) {
                if (fromLangText == "Indonesia") {
                    viewModel.translateIndoToGayo(textToTranslate)
                } else {
                    viewModel.translateGayoToIndo(textToTranslate)
                }
            }
            viewModel.translatedText.observe(viewLifecycleOwner) { translatedText ->
                val inputText = binding.editTextInput.text.toString()
                val fromLang = binding.textFrom.text.toString()
                val toLang = binding.textToLang.text.toString()
                val outputText = translatedText ?: "Failed to translate"
                if (inputText.isEmpty()) {
                    Toast.makeText(requireContext(), "Anda belum menerjemahkan apapun", Toast.LENGTH_SHORT).show()
                    return@observe
                }
                val fromLangAbbreviation = if (fromLang == "Indonesia") "In" else "gy"
                val toLangAbbreviation = if (toLang == "Indonesia") "In" else "gy"
                val historyEntity = HistoryEntity(
                    inLang = fromLangAbbreviation,
                    inLangHistory = inputText,
                    gyLang = toLangAbbreviation,
                    gyLangHistory = outputText
                )
                viewModel.addHistory(historyEntity)
                Toast.makeText(requireContext(), "Data dimasukkan ke history", Toast.LENGTH_SHORT).show()
                viewModel.translatedText.removeObservers(viewLifecycleOwner)
            }
        }

        binding.replace.setOnClickListener {
            onReplaceClicked()
        }

        viewModel.translatedText.observe(viewLifecycleOwner) { translatedText ->
            binding.textOutputUser.text = translatedText
        }
        binding.copyImageOutput.setOnClickListener {
            copyTextToClipboard()
        }

        binding.shareImageOutput.setOnClickListener {
            shareText()
        }

        binding.pasteButton.setOnClickListener {
            pasteTextFromClipboard()
        }

        binding.cancelButton.setOnClickListener {
            clearEditText()
        }
        // add favorite
        binding.starImageOutput.setOnClickListener {

            val inputText = binding.editTextInput.text.toString()
            val fromLang = binding.textFrom.text.toString()
            val toLang = binding.textToLang.text.toString()
            val outputText = binding.textOutputUser.text.toString()

            if (inputText.isEmpty()) {
                Toast.makeText(requireContext(), "Anda belum menerjemahkan apapun", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fromLangAbbreviation = if (fromLang == "Indonesia") "In" else "gy"
            val toLangAbbreviation = if (toLang == "Indonesia") "In" else "gy"

            val favoriteEntity = FavoriteEntity(
                inLang = fromLangAbbreviation,
                inLangFavorite = inputText,
                gyLang = toLangAbbreviation,
                gyLangFavorite = outputText
            )
            viewModel.insertFavorite(favoriteEntity)
            Toast.makeText(requireContext(), "Data berhasil disimpan ke favorit", Toast.LENGTH_SHORT).show()
        }

        updateLanguageLabels()
<<<<<<< HEAD

        speechToTextConverter = SpeechToTextConverter(requireContext(), this)
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.RECORD_AUDIO),
                1
            )
        }

        binding.recordImage.setOnClickListener {
            speechToTextConverter.startListening("id")
        }

    }
    fun setText(recognizedText: String) {
        binding.editTextInput.setText(recognizedText)
=======
>>>>>>> 8d13816 (second)
    }

    fun onReplaceClicked() {
        val temp = fromLangText
        fromLangText = toLangText
        toLangText = temp
        updateLanguageLabels()
    }

    private fun updateLanguageLabels() {
        binding.fromLang.text = fromLangText
        binding.textFrom.text = fromLangText

        binding.toLang.text = toLangText
        binding.textToLang.text = toLangText
    }
    private fun copyTextToClipboard() {
        val textToCopy = binding.textOutputUser.text.toString()
        val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(requireContext(), "Teks berhasil dicopy", Toast.LENGTH_SHORT).show()
    }

    private fun shareText() {
        val textToShare = binding.textOutputUser.text.toString()
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textToShare)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun pasteTextFromClipboard() {
        val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (clipboardManager.hasPrimaryClip()) {
            val clipData = clipboardManager.primaryClip
            val item = clipData?.getItemAt(0)
            val clipboardText = item?.text.toString()
            binding.editTextInput.setText(clipboardText)
            Toast.makeText(requireContext(), "Teks ditaruh...", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Clipboard kosong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearEditText() {
        binding.editTextInput.text?.clear()
        Toast.makeText(requireContext(), "Text direset", Toast.LENGTH_SHORT).show()
    }

<<<<<<< HEAD
    override fun onDestroy() {
        super.onDestroy()
        speechToTextConverter.stopListening()
    }
    override fun onReadyForSpeech() {
        binding.recordImage.visibility = View.GONE
        binding.editTextInput.visibility = View.GONE
        binding.buttonTranslate.visibility= View.GONE
        binding.tvMessage.visibility = View.VISIBLE
        binding.lavMicAnimation.visibility = View.VISIBLE
    }
    override fun onBeginningOfSpeech() {}
    override fun onEndOfSpeech() {}
    override fun onError(error: String) {
        val context = context ?: return // Check if context is available
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()

        activity?.runOnUiThread {
            binding.recordImage.visibility = View.VISIBLE
            binding.editTextInput.visibility = View.VISIBLE
            binding.buttonTranslate.visibility = View.VISIBLE
            binding.editTextInput.text = Editable.Factory.getInstance().newEditable("Sorry, Please try again")
            binding.tvMessage.visibility = View.GONE
            binding.lavMicAnimation.visibility = View.GONE
        }
    }



    override fun onResults(results: String) {
        binding.recordImage.visibility = View.VISIBLE
        binding.editTextInput.visibility = View.VISIBLE
        binding.buttonTranslate.visibility = View.VISIBLE
        binding.lavMicAnimation.visibility = View.GONE
        binding.tvMessage.visibility = View.GONE
        binding.editTextInput.text = Editable.Factory.getInstance().newEditable(results)
    }


=======
>>>>>>> 8d13816 (second)
}
