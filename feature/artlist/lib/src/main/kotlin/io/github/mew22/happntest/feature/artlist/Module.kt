package io.github.mew22.happntest.feature.artlist

import io.github.mew22.happntest.core.network.NetworkBuilder
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val artListModule = module {
    factory<ArtListService> {
        get<NetworkBuilder>().build().create(ArtListMockService::create)
    }
    factory<ArtListDao> { get<ArtListDatabase>().artListDao() }
    factory<ArtListRepository> { ArtListRepositoryImpl(get(), get()) }
    viewModelOf(::ArtListViewModel)
}
