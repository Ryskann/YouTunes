package com.youtunes

import android.content.ContentValues.TAG
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import androidx.lifecycle.lifecycleScope


class RemoteResources: AppCompatActivity() {
    data class Artist(val name: String)
    data class Event(val name: String)

    fun getFavouriteArtists(limit: Int=10): List<Artist> {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url("https://api.spotify.com/v1/me/top/artists")
                    .addHeader("Authorization", "Bearer " + SpotifyKeyStorage.getBearer())
                    .build()

                val result = OkHttpClient().newCall(request).execute()
                val responseBody = result.body?.string()

                // Faire quelque chose avec la réponse ici (par exemple, afficher dans le log)
                Log.w(TAG, responseBody.toString())
            } catch (e: IOException) {
                // Gérer les erreurs ici
                e.printStackTrace()
            }
        }
        return ArrayList();
    }

    fun getArtistEvents(artist: Artist, limit: Int): List<Event> {
        return ArrayList<Event>()
    }
}