package com.youtunes

import android.content.ContentValues.TAG
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.Base64
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.runBlocking


class SpotifyCallback() :AppCompatActivity(){
    private val client_id=BuildConfig.Spotify_client_id;
    private val client_secret=BuildConfig.Spotify_client_secret;
    private val redirect_uri = "com.youtune://spotify-callback";
    private lateinit var spotifyKeyStorage: SpotifyKeyStorage



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        spotifyKeyStorage = SpotifyKeyStorage(applicationContext)

        val code=intent.data?.getQueryParameter("code")
        val state=intent.data?.getQueryParameter("state");

        if (state == null || code == null) {
            // Gérer l'erreur "state_mismatch" ici
        } else {
            // Exécutez la récupération du jeton dans un AsyncTask
            TokenExchangeAsyncTask().execute(code)
        }
    }
    private inner class TokenExchangeAsyncTask : AsyncTask<String, Void, Unit>() {
        override fun doInBackground(vararg params: String?) {
            val code = params[0]
            if (code != null) {
                val client = OkHttpClient()
                val formBody: RequestBody = FormBody.Builder()
                    .add("code", code)
                    .add("redirect_uri", redirect_uri)
                    .add("grant_type", "authorization_code")
                    .build()

                val base64Auth = Base64.getEncoder()
                    .encodeToString("$client_id:$client_secret".toByteArray())
                val request = Request.Builder()
                    .url("https://accounts.spotify.com/api/token")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Authorization", "Basic $base64Auth")
                    .post(formBody)
                    .build()
                //penser à gérer si mauvais mdp indiquer sur spotify (retour surement à la page de Login)
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()

                // Analysez la réponse JSON ici
                val jsonResponse = JSONObject(responseBody)
                Log.w(TAG,jsonResponse.toString())
                spotifyKeyStorage.writeSpotifyKey(jsonResponse)
            }
        }

        override fun onPostExecute(result: Unit) {
            // Actions après la récupération du jeton, si nécessaire
        }
    }


}