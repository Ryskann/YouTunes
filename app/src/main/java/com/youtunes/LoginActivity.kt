package com.youtunes

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.SecureRandom
import okhttp3.HttpUrl
import java.io.File

class LoginActivity : AppCompatActivity() {
    private val client_id = BuildConfig.Spotify_client_id;
    private val redirect_uri = "com.youtune://spotify-callback";
    private val scope = "user-top-read";
    private lateinit var fileDir: File;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        fileDir = this.filesDir
        if (ChecksIfTheUseHasASecretId(fileDir)) {
            val intent = Intent(this@LoginActivity, SpotifyCallback::class.java)
            startActivity(intent)
            finish()
        } else {
            Log.w(TAG, "On passe ici ")
            callSpotifyAPIForGetSpotifyKey()
        }
    }

    /**
     * fonction a implémenter quand on auras fait la première partis de la connexion
     * Cette fonction permet de vérifier si l'utilisateur à déja connecter sont compte
     * spotify une fois pour pouvoir utiliser le secretID
     * au lieu de refaire toute les demande ce qui est lourd
     * @return false si l'utilisateur c'est jamais connecter, true dans le cas inverse
     */
    fun ChecksIfTheUseHasASecretId(fileDir: File): Boolean {
        return SpotifyKeyStorage.getRefrecher(fileDir) != ""
    }

    /**
     * Fonction à implementer
     */
    private fun getNewSpotifyKeyBySecretId() {
        throw NotImplementedError("Methode non implémenter");
    }

    /**
     * Permet d'appeler l'API de spotify pour récupérer les autorization nécessaire
     * pour le fonctionnement de l'application
     */
    private fun callSpotifyAPIForGetSpotifyKey() {
        lifecycleScope.launch(Dispatchers.IO) {
            //on prépare la requete
            var state = generateRandomString(16);
            val authorizeUrl = HttpUrl.Builder()
                .scheme("https")
                .host("accounts.spotify.com")
                .addPathSegments("authorize")
                .addQueryParameter("response_type", "code")
                .addQueryParameter("client_id", client_id)
                .addQueryParameter("scope", scope)
                .addQueryParameter("redirect_uri", redirect_uri)
                .addQueryParameter("state", state)
                .build()
            redirectAction(authorizeUrl)
        }
    }

    /**
     * fonction qui permet de générer une suite de caractère aléatoire
     * info pas obligatoire mais très recommandé par spotify car
     * This provides protection against attacks such as cross-site request forgery.
     * Plus d'info ici : https://developer.spotify.com/documentation/web-api/tutorials/code-flow
     * @param length : Longueur de la chaine de caractère
     */
    private fun generateRandomString(length: Int): String {
        val characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        val random = SecureRandom()
        val sb = StringBuilder(length)

        for (i in 0 until length) {
            val randomChar = characters[random.nextInt(characters.length)]
            sb.append(randomChar)
        }
        return sb.toString()
    }

    /**
     * Permet de rediriger vers la page
     * @param authorizeUrl : lien de la page web de spotify pour s'authentifier
     */
    private fun redirectAction(authorizeUrl: HttpUrl) {
        val redirectUrl = authorizeUrl.toString()
        val intent = Intent(Intent.ACTION_VIEW)
        val uri = Uri.parse(redirectUrl)
        Log.w(TAG, "URL")
        Log.w(TAG, uri.toString())
        intent.data = uri
        startActivity(intent)
    }
}