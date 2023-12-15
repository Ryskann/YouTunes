package com.youtunes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File

class ArtistViewModel : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    data class Artist(val name: String, val imageSrc: String)
    data class Event(val name: String)

    fun getFavouriteArtists(limit: Int = 10, timeRange: String = "short_term", fileDir: File) {
        val artistList = ArrayList<Artist>()
        //appel API
        viewModelScope.launch(Dispatchers.IO) {
            val responseBody = RemoteResources.getFavouriteArtists(limit, timeRange, fileDir)
            val jsonResponse = JSONObject(responseBody)
            for (i in 0 until jsonResponse.getJSONArray("items").length()) {
                val artistData = jsonResponse.getJSONArray("items").getJSONObject(i)
                val artist = Artist(
                    artistData.getString("name"),
                    artistData.getJSONArray("images").getJSONObject(0).getString("url")
                )
                artistList.add(artist)
            }
            withContext(Dispatchers.Main) {
                _name.value = artistList.get(0).name
            }
        }

    }

    fun getArtistEvents(artist: Artist, limit: Int): List<Event> {
        return ArrayList<Event>()
    }
}