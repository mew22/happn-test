package io.github.mew22.happntest.core.db

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    factory<DatabaseHelper> { DatabaseFactory(androidContext()) }
}
