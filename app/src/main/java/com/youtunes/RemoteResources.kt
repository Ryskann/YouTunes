package com.youtunes

import android.content.ContentValues.TAG
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import androidx.lifecycle.lifecycleScope
import java.io.File


class RemoteResources: AppCompatActivity() {
    data class Artist(val name: String)
    data class Event(val name: String)

     fun getFavouriteArtists(limit: Int = 10, time_range:String="short_term",fileDir: File): List<Artist> {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url("""https://api.spotify.com/v1/me/top/artists?time_range=$time_range&limit=$limit""")
                    .addHeader("Authorization", "Bearer " + SpotifyKeyStorage.getBearer(fileDir))
                    .build()

                val result = OkHttpClient().newCall(request).execute()
                val responseBody = result.body?.string()

                // Faire quelque chose avec la réponse ici (par exemple, afficher dans le log)
                Log.w(TAG, responseBody.toString())

            }catch (error:Error){

            }
        }
        return ArrayList()
    }


    fun getArtistEvents(artist: Artist, limit: Int): List<Event> {
        return ArrayList<Event>()
    }
}