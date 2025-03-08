package io.github.mew22.happntest.feature.artlist

import kotlinx.coroutines.delay

object ArtListMockService {

    var artList: List<ArtListRemoteData> = listOf(
        ArtListRemoteData(
            id = "1",
            objectNumber = "1",
            title = "Title 1",
            principalOrFirstMaker = "Artist 1",
            webImage = WebImage(
                url = "https://lh3.googleusercontent.com/SsEIJWka3_cYRXXSE8VD3XNOgtOxoZhqW1u" +
                        "B6UFj78eg8gq3G4jAqL4Z_5KwA12aD7Leqp27F653aBkYkRBkEQyeKxfaZPyDx0O8CzWg=s0"
            )
        ),
        ArtListRemoteData(
            id = "2",
            objectNumber = "2",
            title = "Title 2",
            principalOrFirstMaker = "Artist 2",
            webImage = WebImage(
                url = "https://lh3.googleusercontent.com/SsEIJWka3_cYRXXSE8VD3XNOgtOxoZhqW1uB6UF" +
                        "j78eg8gq3G4jAqL4Z_5KwA12aD7Leqp27F653aBkYkRBkEQyeKxfaZPyDx0O8CzWg=s0"
            )
        ),
        ArtListRemoteData(
            id = "3",
            objectNumber = "3",
            title = "Title 3",
            principalOrFirstMaker = "Artist 3",
            webImage = WebImage(
                url = "https://lh3.googleusercontent.com/SsEIJWka3_cYRXXSE8VD3XNOgtOxoZhqW1uB" +
                        "6UFj78eg8gq3G4jAqL4Z_5KwA12aD7Leqp27F653aBkYkRBkEQyeKxfaZPyDx0O8CzWg=s0"
            )
        ),
    )

    fun create() = object : ArtListService {
        override suspend fun fetchArtList(): Result<ArtListDataResponse> {
            delay(timeMillis = 2000)
            return Result.success(ArtListDataResponse(artList))
        }
    }
}
