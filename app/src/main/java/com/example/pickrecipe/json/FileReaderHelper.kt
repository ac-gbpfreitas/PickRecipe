package com.example.pickrecipe.json

import android.content.Context
import android.util.Log
import java.io.BufferedReader

class FileReaderHelper {
    companion object {
        // accepts Context and filename
        fun getDataFromAssets(context: Context, fileName: String): String {
            return context.assets.open(fileName).use {
                it.bufferedReader().use { bf: BufferedReader ->
                    bf.readText()
                }
            }
        }
    }
}