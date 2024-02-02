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
    private val _artistsData = MutableLiveData<List<Artist>>()
    val artistsData: LiveData<List<Artist>>
        get() = _artistsData

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
                    artistData.getJSONArray("images").getJSONObject( artistData.getJSONArray("images").length()-1).getString("url")
                )
                artistList.add(artist)
            }
            withContext(Dispatchers.Main) {
                _artistsData.value = artistList
            }
        }
    }
}