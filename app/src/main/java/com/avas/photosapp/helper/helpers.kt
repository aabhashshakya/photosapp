package com.avas.photosapp.helper

/**
 * Created by Aabhash Shakya on 4/10/2024
 */
fun isTriangular(z: Int): Boolean {
    var sum = 0

    for (i in 1..z) {
        sum += i
        if (sum == z) {
            return true
        }
    }
    return false
}