package com.youtunes

class RemoteResources {
    data class Artist(val name: String)
    data class Event(val name: String)

    fun getFavouriteArtists(limit: Int): List<Artist> {
        return ArrayList<Artist>()
    }

    fun getArtistEvents(artist: Artist, limit: Int): List<Event> {
        return ArrayList<Event>()
    }
}