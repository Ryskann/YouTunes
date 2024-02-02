package com.youtunes

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.net.URI

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class EventItemAdapter(private val context: Context,private val dataset:List<Event>) : RecyclerView.Adapter<EventItemAdapter.EventItemHolder>(){
    class EventItemHolder(private val view: View): RecyclerView.ViewHolder(view){
        val eventPlace :TextView=view.findViewById(R.id.eventPlace)
        val eventCity: TextView=view.findViewById(R.id.eventCity)
        val eventDate: TextView=view.findViewById(R.id.eventDate)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventItemHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.event_item,parent,false)
        return EventItemHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: EventItemHolder, position: Int) {
        val item = dataset[position]
        holder.eventPlace.text = item.eventPlace
        holder.eventCity.text = item.eventCity
        holder.eventDate.text = item.eventDate.dayOfMonth.toString() + "/" + item.eventDate.month.toString() + "/" + item.eventDate.year.toString()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}