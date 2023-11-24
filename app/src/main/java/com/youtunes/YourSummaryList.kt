package com.youtunes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class YourSummaryListActivity : AppCompatActivity() {
    private lateinit var fileDir: File;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_summary);
        fileDir = this.filesDir
        RemoteResources().getFavouriteArtists(1, fileDir)
    }
}