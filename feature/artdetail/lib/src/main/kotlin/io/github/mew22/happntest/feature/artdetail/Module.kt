package io.github.mew22.happntest.feature.artdetail

import io.github.mew22.happntest.core.network.NetworkBuilder
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val artDetailModule = module {
    factory<ArtDetailService> {
        get<NetworkBuilder>().build().create(ArtDetailMockService::create)
    }
    factory<ArtDetailDao> { get<ArtDetailDatabase>().artDetailDao() }
    factory<ArtDetailRepository> { ArtDetailRepositoryImpl(get(), get()) }
    viewModelOf(::ArtDetailViewModel)
}
