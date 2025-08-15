package com.example.youtubenext

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView: WebView = findViewById(R.id.webView)

        // Verhindert, dass Links im externen Browser geöffnet werden
        webView.webViewClient = WebViewClient()

        // WebView konfigurieren (gemäß AGENTS.md)
        webView.settings.apply {
            javaScriptEnabled = true // JavaScript aktivieren
            domStorageEnabled = true // DOM Storage für Logins etc. aktivieren
            // Setzt den Desktop User-Agent, damit YouTube die Desktop-Seite lädt
            userAgentString = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36"
        }

        // YouTube-Webseite laden
        webView.loadUrl("https://www.youtube.com")
    }
}