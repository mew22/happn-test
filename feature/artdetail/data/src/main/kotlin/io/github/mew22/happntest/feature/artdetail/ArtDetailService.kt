package io.github.mew22.happntest.feature.artdetail

import retrofit2.http.GET
import retrofit2.http.Path

interface ArtDetailService {

    @GET("api/nl/collection/{number}")
    suspend fun fetchArtDetail(@Path("number") number: String): Result<ArtDetailDataResponse>
}
