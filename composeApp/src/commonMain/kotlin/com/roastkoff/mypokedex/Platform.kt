package com.roastkoff.mypokedex

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform