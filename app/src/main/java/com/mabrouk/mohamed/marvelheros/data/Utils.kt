package com.mabrouk.mohamed.marvelheros.data

import java.security.MessageDigest

fun generateMarvelApiHash(ts: String, publicKey: String, privateKey: String): String {
    val input = ts + privateKey + publicKey
    val md = MessageDigest.getInstance("MD5")
    val hashBytes = md.digest(input.toByteArray())
    return hashBytes.joinToString("") { "%02x".format(it) } // Convert bytes to hex
}