package com.marvel.remote.retrofit.data

import com.marvel.util.Encryption


object VALUES {
    private const val privateKey = "03533c803fea71c5d95f02a49c84c42851515e49"
    const val apiKey = "497e82c76327db482b2c154f2fa232f5"
    const val timeStamp = "123"
    val hash = Encryption().md5(timeStamp + privateKey + apiKey)
}