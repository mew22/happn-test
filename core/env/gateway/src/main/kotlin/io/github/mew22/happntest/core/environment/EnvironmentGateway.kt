package io.github.mew22.happntest.core.environment

interface EnvironmentGateway {
    val isDebug: Boolean
    val isRelease: Boolean
    val isMock: Boolean
    val isProd: Boolean
    val androidVersion: Int
    val apiKey: String
}
