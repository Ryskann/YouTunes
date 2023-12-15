package com.youtunes

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.io.File

class YourSummaryListActivity : AppCompatActivity() {
    private lateinit var fileDir: File;
    private lateinit var artistViewModel: ArtistViewModel
    private lateinit var testTextView: TextView // Assurez-vous d'avoir la référence à votre TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_summary);
        fileDir = this.filesDir
        testTextView = findViewById(R.id.testNom)
        artistViewModel = ViewModelProvider(this).get(ArtistViewModel::class.java)
        artistViewModel.name.observe(this) { name ->
            testTextView.text = name
        }
        artistViewModel.getFavouriteArtists(10, "short_term", fileDir)
    }
}