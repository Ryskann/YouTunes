package com.youtunes

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class EventItemAdapter(private val context: Context,private val dataset:List<Event>) : RecyclerView.Adapter<EventItemAdapter.EventItemHolder>(){
    class EventItemHolder(private val view: View): RecyclerView.ViewHolder(view){
        val eventPlace :TextView=view.findViewById(R.id.eventPlace)
        val eventCity: TextView=view.findViewById(R.id.eventCity)
        val eventDate: TextView=view.findViewById(R.id.eventDate)
        val TMButton: Button = view.findViewById(R.id.TM_button)
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

        holder.TMButton.setOnClickListener{
            val url = item.ticketMasterUrl
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}