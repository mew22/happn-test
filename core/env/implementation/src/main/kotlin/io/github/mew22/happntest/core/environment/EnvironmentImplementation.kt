package io.github.mew22.happntest.core.environment

import android.os.Build
import io.github.mew22.happntest.core.env.implementation.BuildConfig

internal class EnvironmentImplementation : EnvironmentGateway {

    override val isDebug: Boolean
        get() = BuildConfig.DEBUG

    override val isRelease: Boolean
        get() = BuildConfig.BUILD_TYPE.contains(RELEASE)

    override val isMock: Boolean
        get() = BuildConfig.FLAVOR == MOCK

    override val isProd: Boolean
        get() = BuildConfig.FLAVOR == PROD

    override val androidVersion: Int
        get() = Build.VERSION.SDK_INT

    override val apiKey: String
        get() = BuildConfig.API_KEY

    private companion object {
        const val RELEASE = "release"
        const val MOCK = "mock"
        const val PROD = "prod"
    }
}
