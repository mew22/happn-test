package io.github.mew22.happntest.core.network

class Api {
    fun getApi(): String = API_ENDPOINT
    companion object {
        private const val API_ENDPOINT = "https://www.rijksmuseum.nl/"
    }
}
