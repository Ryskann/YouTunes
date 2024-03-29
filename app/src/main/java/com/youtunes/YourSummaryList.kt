package com.youtunes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class YourSummaryListActivity : AppCompatActivity() {
    private lateinit var fileDir: File;
    private lateinit var artistViewModel: ArtistViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        fileDir = this.filesDir
        val recyclerView=findViewById<RecyclerView>(R.id.recycle_view)
        artistViewModel = ViewModelProvider(this).get(ArtistViewModel::class.java)
        artistViewModel.artistsData.observe(this) { data ->
            recyclerView.adapter=ArtistItemAdapter(this,data);
        }
        artistViewModel.getFavouriteArtists(10, "short_term", fileDir)
    }
}