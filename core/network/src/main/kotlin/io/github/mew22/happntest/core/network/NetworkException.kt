package io.github.mew22.happntest.core.network

data class NetworkException(
    val httpCode: Int,
) : RuntimeException()
