package com.youtunes

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class YourSummaryListActivity : AppCompatActivity() {
    private lateinit var fileDir: File;
    private lateinit var artistViewModel: ArtistViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_summary);
        fileDir = this.filesDir
        val recyclerView=findViewById<RecyclerView>(R.id.recycle_view_artist)
        artistViewModel = ViewModelProvider(this).get(ArtistViewModel::class.java)
        artistViewModel.artistData.observe(this) { data ->
            recyclerView.adapter=ArtistItemAdapter(this,data);
        }
        artistViewModel.getFavouriteArtists(10, "short_term", fileDir)
    }
}