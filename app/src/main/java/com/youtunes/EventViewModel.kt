package com.youtunes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDate

class EventViewModel : ViewModel() {
    private val _eventsData = MutableLiveData<List<Event>>()
    val eventsData: LiveData<List<Event>>
        get() = _eventsData

    fun getArtistEvents(keyword: String) {
        val eventsList = ArrayList<Event>()
        lateinit var jsonResponse: JSONObject
        //appel API
        viewModelScope.launch(Dispatchers.IO) {
            val responseBody = RemoteResources.getArtistsEvents(keyword)
            try{
                val jsonResponse = JSONObject(responseBody).getJSONObject("_embedded")

                val eventsJsonArray = jsonResponse.getJSONArray("events")
                for ( i in 0 until eventsJsonArray.length()) {
                    val eventData = eventsJsonArray.getJSONObject(i)
                    val event = Event(
                        ticketMasterUrl = "a",
                        eventDate = LocalDate.parse(eventData.getJSONObject("dates").getJSONObject("start").getString("localDate")),
                        eventPlace = eventData.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getString("name"),
                        eventCity = eventData.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("city").getString("name") + ", " + eventData.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("country").getString("name"),
                    )
                    eventsList.add(event)
                }
            } catch (e : Exception){
                val a = e
                //no event
            }

            //TODO fill eventsList
            val e = eventsList


            withContext(Dispatchers.Main) {
                _eventsData.value = eventsList
            }
        }
    }
}