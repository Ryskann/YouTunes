package com.youtunes

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.security.SecureRandom
import okhttp3.HttpUrl

class LoginActivity :AppCompatActivity() {
    private val client_id = BuildConfig.Spotify_client_id;
    private val redirect_uri = "com.youtune://spotify-callback";
    private val scope = "user-top-read";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (ChecksIfTheUseHasASecretId()) {
            getNewSpotifyKeyBySecretId();
        } else {
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
    fun ChecksIfTheUseHasASecretId(): Boolean {
        return false
    }

    /**
     * Fonction à implementer
     */
    fun getNewSpotifyKeyBySecretId() {
        throw NotImplementedError("Methode non implémenter");
    }

    fun callSpotifyAPIForGetSpotifyKey() {
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
        //on exécute la requete
        RedirectAsyncTask().execute(authorizeUrl.toString())

    }

    /**
     * fonction qui permet de générer une suite de charactère aléatoire
     * info pas obligatoire mais très recommandé par spotify car
     * This provides protection against attacks such as cross-site request forgery.
     * Plus d'info ici : https://developer.spotify.com/documentation/web-api/tutorials/code-flow
     */
    fun generateRandomString(length: Int): String {
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
     * Class qui permet de faire l'appel API sur un thread séparer
     * (et donc impacte pas l'application)
     * AsyncTask est deprecated donc voir si pas moyen de faire autrement
     * Voir si aussi pas moyen de faire ceci dans une class public pour pouvoir
     * l'appeler à plusieurs endroit
     * (vus qu'on bouge après sur la page
     */
    private inner class RedirectAsyncTask : AsyncTask<String, Void, Unit>() {
        override fun doInBackground(vararg params: String?) {
            val redirectUrl = params[0]
            val intent = Intent(Intent.ACTION_VIEW)
            val uri = Uri.parse(redirectUrl)
            Log.w(TAG,"URL")
            Log.w(TAG,uri.toString())
            intent.data = uri
            startActivity(intent)
        }
    }
}