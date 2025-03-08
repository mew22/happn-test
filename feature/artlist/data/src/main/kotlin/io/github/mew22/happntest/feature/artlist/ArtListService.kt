package io.github.mew22.happntest.feature.artlist

import retrofit2.http.GET

interface ArtListService {

    @GET("api/nl/collection?p=1&ps=100")
    suspend fun fetchArtList(): Result<ArtListDataResponse>
}
