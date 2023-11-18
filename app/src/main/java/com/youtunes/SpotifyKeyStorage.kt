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


class SpotifyKeyStorage(context:Context) {
    private val Context.dataStore by preferencesDataStore(name = "spotify_preferences")
    private val dataStore: DataStore<Preferences> = context.dataStore
    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    fun writeSpotifyKey(jsonData: JSONObject){
        val accessToken=jsonData["access_token"]
        val refreshToken=jsonData["refresh_token"]

        runBlocking {
            dataStore.edit { data ->
                data[ACCESS_TOKEN_KEY] = accessToken.toString()
                data[REFRESH_TOKEN_KEY] = refreshToken.toString()
            }
        }
    }

    fun readSpotifyAccessToken():String{
        return runBlocking {
            dataStore.data.first()[ACCESS_TOKEN_KEY] ?: ""
        }
    }

    fun readSpotifyRefreshToken(): String {
        return runBlocking {
            dataStore.data.first()[REFRESH_TOKEN_KEY] ?: ""
        }
    }
}