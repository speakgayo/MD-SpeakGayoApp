package com.khairililmi.speakgayo.ui.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.khairililmi.speakgayo.data.local.favorite.FavoriteEntity
import com.khairililmi.speakgayo.databinding.FragmentHomeBinding
import com.khairililmi.speakgayo.ui.favorite.AppFavoriteDb
import com.khairililmi.speakgayo.ui.favorite.FavoriteRepository
import com.khairililmi.speakgayo.ui.favorite.FavoriteViewModel
import com.khairililmi.speakgayo.ui.favorite.FavoriteViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: TranslateViewModel
    private var fromLangText = "Indonesia"
    private var toLangText = "Gayo"

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
        val repository = TranslateRepository(favoriteDao)
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
        binding.editTextInput.text.clear()
        Toast.makeText(requireContext(), "Text direset", Toast.LENGTH_SHORT).show()
    }

}
