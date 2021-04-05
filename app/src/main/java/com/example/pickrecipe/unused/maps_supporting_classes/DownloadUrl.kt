package com.example.pickrecipe.unused.maps_supporting_classes

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class DownloadUrl {

    @Throws(IOException::class)
    fun readUrl(myUrl: String) : String {
        var data = ""
        var inputStream : InputStream? = null
        var httpURLConnection: HttpURLConnection? = null

        try {
            val url = URL(myUrl)
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.connect()

            inputStream = httpURLConnection.inputStream
            val br = BufferedReader(InputStreamReader(inputStream))
            val sb = StringBuffer()

            var line = ""
            while (br.readLine().also { line = it } != null) {
                sb.append(line)
            }

            data = sb.toString()
            br.close()

        } catch (e : MalformedURLException){
            Log.i("DownloadUrl","readUrl: " + e.message)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
            httpURLConnection?.disconnect()
        }

        return data
    }
}