package com.youtunes

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.Base64
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


class SpotifyCallback() : AppCompatActivity() {
    private val client_id = BuildConfig.Spotify_client_id;
    private val client_secret = BuildConfig.Spotify_client_secret;
    private val redirect_uri = "com.youtune://spotify-callback";
    private lateinit var fileDir: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fileDir=this.filesDir
        var code = intent.data?.getQueryParameter("code")
        Log.w(TAG,code.toString())
        if (code == null) {
            val refreshToken = SpotifyKeyStorage.getRefrecher(fileDir)
            updateSpotifyToken(refreshToken)
        } else {
            getSpotifyToken(code)
        }
    }

     fun getSpotifyToken(code: String) {
        lifecycleScope.launch(Dispatchers.IO) {
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
                response.close()

                // Analysez la réponse JSON ici
                val jsonResponse = JSONObject(responseBody)
                Log.w(TAG, jsonResponse.toString())
                SpotifyKeyStorage.writeSpotifyKey(jsonResponse,fileDir)
                redirectToApplication()
            }
        }
    }

    private fun updateSpotifyToken(refreshToken:String){
        lifecycleScope.launch(Dispatchers.IO) {
            if (refreshToken != null) {
                val client = OkHttpClient()
                Log.w(TAG,refreshToken);
                val formBody: RequestBody = FormBody.Builder()
                    .add("client_id", client_id)
                    .add("refresh_token", refreshToken)
                    .add("grant_type", "refresh_token")
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
                Log.w(TAG,request.toString())
                val response = client.newCall(request).execute()
                Log.w(TAG,response.toString())
                val responseBody = response.body?.string()
                response.close()
                // Analysez la réponse JSON ici
                val jsonResponse = JSONObject(responseBody)
                Log.w(TAG, jsonResponse.toString())
                SpotifyKeyStorage.writeSpotifyKey(jsonResponse,fileDir)
                redirectToApplication()
            }
        }
    }
    private fun redirectToApplication() {
        val intentPostExecution = Intent(this@SpotifyCallback, YourSummaryListActivity::class.java)
        startActivity(intentPostExecution)
        finish()
    }
}