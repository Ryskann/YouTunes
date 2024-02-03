package com.youtunes

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class EventDetailsActivity : AppCompatActivity() {
    private lateinit var fileDir: File;
    private lateinit var eventViewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val keyword: String = intent.extras!!.getString("keyword", "no_keyword")
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        fileDir = this.filesDir
        val recyclerView=findViewById<RecyclerView>(R.id.recycle_view)
        eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        eventViewModel.eventsData.observe(this) { data ->
            if (data.isEmpty()){
                findViewById<TextView>(R.id.alertText).setText(R.string.no_data_alert)
            } else {
                recyclerView.adapter=EventItemAdapter(this,data);
            }
        }
        eventViewModel.getArtistEvents(keyword)
    }
}