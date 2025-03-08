package io.github.mew22.happntest.core.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonTestTodo(val id: Long, val title: String, val completed: Boolean)
