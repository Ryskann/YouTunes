package com.youtunes

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

object RemoteResources {
    suspend fun getFavouriteArtists(
        limit: Int = 10, time_range: String = "short_term",
        fileDir: File
    ): String? {
        try {
            val request = Request.Builder()
                .url("""https://api.spotify.com/v1/me/top/artists?time_range=$time_range&limit=$limit""")
                .addHeader("Authorization", "Bearer " + SpotifyKeyStorage.getBearer(fileDir))
                .build()

            val result = OkHttpClient().newCall(request).execute()
            val responseBody = result.body?.string()
            return responseBody
        } catch (error: Error) {
            // Gérer l'erreur ici
            throw error;
        }
    }
}