package io.github.mew22.happntest.core.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface TestService {

    @GET("todos")
    suspend fun getTodos(): Result<List<JsonTestTodo>>

    @GET("todos")
    fun getTodosSync(): Result<List<JsonTestTodo>>

    @GET("todos")
    suspend fun getTodosWithoutResult(): List<JsonTestTodo>

    @GET("todos/{id}")
    suspend fun getSingleTodo(@Path("id") id: Long): Result<JsonTestTodo>

    @POST("todos")
    suspend fun addTodo(@Body todo: JsonTestTodo): Result<Unit>
}
