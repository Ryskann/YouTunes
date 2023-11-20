package com.youtunes

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import kotlinx.coroutines.flow.first


class SpotifyKeyStorage() {
    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")

    companion object {
        private lateinit var bearer: String
        private lateinit var refresher: String

        @JvmStatic fun getBearer(): String {
            return bearer;
        }

        @JvmStatic fun getRefrecher(): String {
            return refresher;
        }
    }

    fun writeSpotifyKey(jsonData: JSONObject){
        bearer =jsonData["access_token"].toString()
        refresher =jsonData["refresh_token"].toString()
    }
}