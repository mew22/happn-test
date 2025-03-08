package io.github.mew22.happntest.core.environment

import org.koin.dsl.module

val envModule = module {
    single<EnvironmentGateway> { EnvironmentImplementation() }
}
