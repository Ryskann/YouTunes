package com.youtunes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class YourSummaryListActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_summary);
        RemoteResources().getFavouriteArtists(1)
    }

}