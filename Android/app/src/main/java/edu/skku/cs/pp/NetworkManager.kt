package edu.skku.cs.pp

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.CompletableDeferred
import okhttp3.*
import java.io.IOException

object NetworkManager {

    private val client = OkHttpClient()
    private val gson = Gson()

    suspend fun <T> fetch(path: String, clazz: Class<T>, headers: Map<String, String>? = null): T {
        val result = CompletableDeferred<T>()

        val requestBuilder = Request.Builder().url(path)
        headers?.forEach {
            requestBuilder.addHeader(it.key, it.value)
        }
        val request = requestBuilder.build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.w("Network Manager", "Network failed at path $path, $e")
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    Log.w("Network Manager", "Network failed at path $path, code: ${response.code}")
                    return
                }

                val responseStr = response.body?.string()
                result.complete(gson.fromJson(responseStr, clazz))
            }
        })

        return result.await()
    }
}