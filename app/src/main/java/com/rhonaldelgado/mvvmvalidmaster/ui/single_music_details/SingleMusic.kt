package com.rhonaldelgado.mvvmvalidmaster.ui.single_music_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.lifecycle.Observer
import com.rhonaldelgado.mvvmvalidmaster.R
import com.rhonaldelgado.mvvmvalidmaster.data.repository.NetworkState
import kotlinx.android.synthetic.main.activity_single_music.*
class SingleMusic : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_music)

        val url: String = intent.getStringExtra("url")

        var webview: WebView = WebView(this);
        setContentView(webview);
        webview.loadUrl(url);


    }
}
