package io.github.mew22.happntest.core.network

import com.squareup.moshi.Moshi
import io.github.mew22.happntest.core.environment.EnvironmentGateway
import io.github.mew22.happntest.core.network.resultadapter.ResultCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val networkModule = module {
    factory {
        HttpLoggingInterceptor().setLevel(
            if (get<EnvironmentGateway>().isDebug) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        )
    }
    factory {
        ApiInterceptor(get())
    }
    factory { Moshi.Builder() }
    factory { Moshi.Builder().build() }
    single {
        OkHttpClient.Builder()
            .connectTimeout(NetworkConfig.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NetworkConfig.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NetworkConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<ApiInterceptor>())
            .build()
    }
    factory {
        ResultCallAdapterFactory(get())
    }
    factory { Api() }
    factory {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .addCallAdapterFactory(get<ResultCallAdapterFactory>())
            .baseUrl(get<Api>().getApi())
    }
    factory {
        NetworkBuilder(get(), get(), get())
    }
}

internal object NetworkConfig {
    const val CONNECTION_TIMEOUT = 40L
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 30L
}
