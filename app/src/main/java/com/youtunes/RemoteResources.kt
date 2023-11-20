package com.youtunes

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request

class RemoteResources: AppCompatActivity() {
    data class Artist(val name: String)
    data class Event(val name: String)

    fun getFavouriteArtists(limit: Int): List<Artist> {
        val a = AsyncSpotifyCall().get()
        return ArrayList()
    }

    fun getArtistEvents(artist: Artist, limit: Int): List<Event> {
        return ArrayList<Event>()
    }

    private inner class AsyncSpotifyCall: AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void?): String {
            val request = Request.Builder()
                .url("https://api.spotify.com/v1/me/top/artists")
                .addHeader("Authorization", "Bearer " + SpotifyKeyStorage.getBearer())
                .build()
            val result = OkHttpClient().newCall(request).execute()
            return result.body?.string() ?: ""
        }
    }
}