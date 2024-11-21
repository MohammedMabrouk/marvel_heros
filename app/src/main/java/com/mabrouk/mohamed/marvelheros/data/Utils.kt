package com.mabrouk.mohamed.marvelheros.data

import java.security.MessageDigest

fun generateMarvelApiHash(ts: String, publicKey: String, privateKey: String): String {
    val input = ts + privateKey + publicKey
    val md = MessageDigest.getInstance("MD5")
    val hashBytes = md.digest(input.toByteArray())
    return hashBytes.joinToString("") { "%02x".format(it) } // Convert bytes to hex
}

fun getLastIntAfterSlash(input: String): Int? {
    val regex = """/(\d+)$""".toRegex() // Matches digits after the last '/'
    return regex.find(input)?.groupValues?.get(1)?.toIntOrNull()
}