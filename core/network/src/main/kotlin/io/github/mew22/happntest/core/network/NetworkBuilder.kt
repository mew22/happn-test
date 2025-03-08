package io.github.mew22.happntest.core.network

import com.squareup.moshi.Moshi
import io.github.mew22.happntest.core.environment.EnvironmentGateway
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NetworkBuilder(
    private val environment: EnvironmentGateway,
    private val moshiBuilder: Moshi.Builder,
    private val retrofitBuilder: Retrofit.Builder,
) {

    private var moshiConfig: Moshi.Builder.() -> Unit = {}

    fun configureMoshi(config: Moshi.Builder.() -> Unit = {}) = apply {
        moshiConfig = config
    }

    fun build(): Network =
        if (environment.isMock) {
            Network(null)
        } else {
            Network(buildRetrofit())
        }

    private fun buildRetrofit(): Retrofit {
        val moshi = buildMoshi()
        return retrofitBuilder
            .addConverterFactory(createMoshiConverter(moshi))
            .build()
    }

    private fun buildMoshi() = moshiBuilder.apply(moshiConfig).build()

    private fun createMoshiConverter(moshi: Moshi) = MoshiConverterFactory.create(moshi).asLenient()
}
