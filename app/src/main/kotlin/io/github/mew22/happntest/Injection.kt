package io.github.mew22.happntest

import io.github.mew22.happntest.core.common.commonModule
import io.github.mew22.happntest.core.db.DatabaseHelper
import io.github.mew22.happntest.core.db.dbModule
import io.github.mew22.happntest.core.environment.envModule
import io.github.mew22.happntest.core.monitoring.monitoringModule
import io.github.mew22.happntest.core.network.networkModule
import io.github.mew22.happntest.feature.artlist.ArtListDatabase
import io.github.mew22.happntest.feature.artlist.artListModule
import org.koin.core.KoinApplication
import org.koin.dsl.binds
import org.koin.dsl.module

val koinConfig: KoinApplication.() -> Unit = {
    modules(
        commonModule,
        envModule,
        monitoringModule,
        networkModule,
        dbModule,
    )
    modules(
        databaseModule,
        artListModule,
    )
}

val databaseModule = module {
    single<Database> {
        get<DatabaseHelper>().create(
            Database::class.java,
            "database.db"
        )
    }.binds(arrayOf(ArtListDatabase::class))
}
