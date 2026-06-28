package com.example

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        textView.text = "Pallavi Graphics Web Workspace is active! Please export to Netlify or Vercel."
        textView.textSize = 18f
        textView.setPadding(40, 40, 40, 40)
        setContentView(textView)
    }
}
