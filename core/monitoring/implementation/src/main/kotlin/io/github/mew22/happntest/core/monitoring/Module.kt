package io.github.mew22.happntest.core.monitoring

import org.koin.dsl.module

val monitoringModule = module {
    single<LogService> { LogServiceImpl() }
}
