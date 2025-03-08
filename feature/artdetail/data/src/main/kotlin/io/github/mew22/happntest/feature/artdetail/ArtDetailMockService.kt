package io.github.mew22.happntest.feature.artdetail

import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlin.random.Random

object ArtDetailMockService {

    var artDetail = ArtDetailRemoteData(
        id = "1",
        objectNumber = "1",
        title = "Title 1",
        description = "Description 1",
        colors = listOf(
            ColorRemoteData(hex = "#A060B0", percentage = 50),
            ColorRemoteData(hex = "#FF1F8F", percentage = 80),
            ColorRemoteData(hex = "#BF1F8F", percentage = 80),
            ColorRemoteData(hex = "#491F2F", percentage = 80),
        ),
        objectTypes = listOf("painting", "sculpture"),
        objectCollection = listOf("collection 1", "collection 2"),
        webImage = WebImage(
            url = "https://lh3.googleusercontent.com/SsEIJWka3_cYRXXSE8VD3XNOgtOxoZhqW1uB6UFj78eg8g" +
                    "q3G4jAqL4Z_5KwA12aD7Leqp27F653aBkYkRBkEQyeKxfaZPyDx0O8CzWg=s0"
        ),
        principalMakers = listOf(
            ArtistRemoteData(
                name = "Artist 1",
                nationality = "Nationality 1"
            ),
            ArtistRemoteData(
                name = "Artist 2",
                nationality = "Nationality 2"
            )
        )
    )

    fun create() = object : ArtDetailService {
        override suspend fun fetchArtDetail(number: String): Result<ArtDetailDataResponse> {
            delay(timeMillis = 2000)
            return Result.success(
                ArtDetailDataResponse(
                    artDetail.copy(
                        objectNumber = number,
                        id = number,
                        colors = artDetail.colors.shuffled(
                            Random(
                                Clock.System.now().toEpochMilliseconds()
                            )
                        )
                    )
                )
            )
        }
    }
}
