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
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.net.URI

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
class ArtistItemAdapter(private val context: Context,private val dataset:List<Artist>) : RecyclerView.Adapter<ArtistItemAdapter.ArtistItemHolder>(){
    class ArtistItemHolder(private val view: View): RecyclerView.ViewHolder(view){
        val artistName:TextView=view.findViewById(R.id.artistName)
        val artistImage:ImageView=view.findViewById(R.id.artistImage)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtistItemHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.artist_item,parent,false)
        return ArtistItemHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ArtistItemHolder, position: Int) {
        val item = dataset[position]
        holder.artistName.text = item.name
        val imageUrl = item.imageSrc

        holder.itemView.setOnClickListener{
            val intent = Intent(context, EventDetailsActivity::class.java)
            intent.putExtra("keyword", item.name)
            context.startActivity(intent)
        }

        Glide.with(context)
            .load(imageUrl)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)) // Optionnel : met en cache l'image
            .into(holder.artistImage)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}