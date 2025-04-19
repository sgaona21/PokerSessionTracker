package com.example.pokersessiontracker;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webView = findViewById(R.id.myWebView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true); // many sites need this
        settings.setDomStorageEnabled(true); // fixes weird cache issues
        settings.setCacheMode(WebSettings.LOAD_DEFAULT); // handles caching better

        // Use WebViewClient to keep navigation inside the WebView
        webView.setWebViewClient(new WebViewClient());

        String url = getIntent().getStringExtra("url");
        if (url != null) {
            webView.loadUrl(url);
        }
    }
}
