package com.khairililmi.speakgayo.customview

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText

class CustomEditText : AppCompatEditText {
    private var errorTextView: TextView? = null
    private var isChangingText = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!isChangingText) {
                    isChangingText = true
                    s?.let {
                        val text = it.toString().trim()
                        val wordCount = countWords(text)
                        val lineCount = countLines(text)

                        if (lineCount > 6) {
                            setText(text.split("\n").take(6).joinToString("\n"))
                            setSelection(text.length)
                        }

                        if (wordCount > 8) {
                            showError("Tidak boleh lebih dari 8 kata")
                        } else {
                            hideError()
                        }
                    }
                    isChangingText = false
                }
            }
        })
    }

    private fun showError(errorMessage: String) {
        if (errorTextView == null) {
            createErrorTextView()
        }
        errorTextView?.apply {
            text = errorMessage
            setTextColor(Color.RED)
            visibility = android.view.View.VISIBLE
        }
    }

    private fun hideError() {
        errorTextView?.visibility = android.view.View.GONE
    }

    private fun createErrorTextView() {
        errorTextView = TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            gravity = Gravity.CENTER
            setPadding(8, 8, 8, 8)
            setBackgroundColor(Color.TRANSPARENT)
            visibility = android.view.View.GONE
        }
        val parentLayout = parent as? ViewGroup ?: return
        parentLayout.addView(errorTextView)
    }

    private fun countWords(text: String): Int {
        return text.split("\\s+".toRegex()).size
    }

    private fun countLines(text: String): Int {
        return text.split("\n").size
    }
}
