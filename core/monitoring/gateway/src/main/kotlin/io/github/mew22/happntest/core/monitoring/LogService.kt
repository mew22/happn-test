package io.github.mew22.happntest.core.monitoring

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}
