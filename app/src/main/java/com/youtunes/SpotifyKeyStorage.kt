package com.youtunes


import android.content.ContentValues.TAG
import android.util.Log
import org.json.JSONObject
import java.io.File


class SpotifyKeyStorage() {
    companion object {
        @JvmStatic
        fun getRefrecher(fileDir: File): String {
            var refreshToken = ""
            try {
                val file = File(fileDir, "SpotifyToken")
                if(file.exists()) {
                    Log.w(TAG, fileDir.toString())
                    var content = file.readText()
                    val refreshTokenRegex = """refresh_token=([^\n]+)""".toRegex()
                    val refreshTokenMatch = refreshTokenRegex.find(content)
                    refreshToken = refreshTokenMatch?.groupValues?.getOrNull(1) ?: ""
                }
            } catch (error: Error) {

            }
            return refreshToken;
        }

        @JvmStatic
        fun getBearer(fileDir: File): String {
            var accessToken = ""
            try {
                val file = File(fileDir, "SpotifyToken")
                if(file.exists()) {
                    var content = file.readText()
                    val accessTokenRegex = """access_token=([^\n]+)""".toRegex()
                    val accessTokenMatch = accessTokenRegex.find(content)
                    accessToken = accessTokenMatch?.groupValues?.getOrNull(1) ?: ""
                }
            } catch (error: Error) {

            }
            return accessToken;
        }

        @JvmStatic
        fun writeSpotifyKey(jsonData: JSONObject, fileDir: File) {
            try {
                val file = File(fileDir, "SpotifyToken")
                val accesToken = jsonData["access_token"].toString()
                val refresh_token = jsonData.optString("refresh_token", "")
                if(refresh_token!="")
                    file.writeText("access_token=" + accesToken + "\nrefresh_token=" + refresh_token + "\n")
                else
                    file.writeText("access_token=" + accesToken + "\n")

            } catch (error: Error) {

            }
        }
    }
}