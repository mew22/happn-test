package io.github.mew22.happntest.core.network

import io.github.mew22.happntest.core.environment.EnvironmentGateway
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor(private val env: EnvironmentGateway) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val url = original.url.newBuilder().addQueryParameter(API_KEY_PARAM, env.apiKey).build()
        val requestBuilder = original.newBuilder()
            .header(AUTH_HEADER, env.apiKey)
            .url(url)
        return chain.proceed(requestBuilder.build())
    }

    private companion object {
        const val API_KEY_PARAM = "key"
        const val AUTH_HEADER = "Authorization"
    }
}
