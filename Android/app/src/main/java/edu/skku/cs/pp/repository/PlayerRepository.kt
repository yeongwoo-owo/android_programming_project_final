package edu.skku.cs.pp.repository

import android.graphics.drawable.Drawable
import edu.skku.cs.pp.NetworkManager
import edu.skku.cs.pp.model.Player
import edu.skku.cs.pp.model.PlayerListDto
import kotlinx.coroutines.*
import java.io.FileNotFoundException
import java.io.InputStream
import java.net.URL

object PlayerRepository {

    private const val BASE_URL = "http://43.200.227.251:8080/players"

    suspend fun findBy(name: String): List<Player> {
        val deferredPlayer = CompletableDeferred<PlayerListDto>()
        CoroutineScope(Dispatchers.IO).launch {
            val player = NetworkManager.fetch(
                path = "$BASE_URL?name=$name",
                PlayerListDto::class.java
            )
            deferredPlayer.complete(player)
        }
        return deferredPlayer.await().players
    }

    suspend fun findBy(id: Long): Player {
        val deferredPlayer = CompletableDeferred<Player>()
        CoroutineScope(Dispatchers.IO).launch {
            val player = NetworkManager.fetch(
                path = "$BASE_URL/$id",
                Player::class.java
            )
            deferredPlayer.complete(player)
        }
        return deferredPlayer.await()
    }

    suspend fun findImage(url: String): Drawable? {
        val deferredImage = CompletableDeferred<Drawable?>()
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            deferredImage.complete(null)
        }
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            val imageSrc = URL(url).content as InputStream
            deferredImage.complete(Drawable.createFromStream(imageSrc, "image"))
        }

        return deferredImage.await()
    }
}