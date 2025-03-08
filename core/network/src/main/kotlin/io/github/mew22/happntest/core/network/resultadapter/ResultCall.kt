package io.github.mew22.happntest.core.network.resultadapter

import com.squareup.moshi.Moshi
import io.github.mew22.happntest.core.network.NetworkException
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class ResultCall<T>(
    private val delegate: Call<T>,
    private val moshi: Moshi,
) : Call<Result<T>> {

    override fun enqueue(callback: Callback<Result<T>>): Unit =
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val result = if (response.isSuccessful) {
                    response.body()?.let { Result.success(it) } ?: Result.failure(
                        BodyNotAvailableException()
                    )
                } else {
                    Result.failure(NetworkException(httpCode = response.code()))
                }
                callback.onResponse(this@ResultCall, Response.success(result))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onResponse(this@ResultCall, Response.success(Result.failure(t)))
            }
        })

    override fun clone(): Call<Result<T>> = ResultCall(delegate, moshi)

    override fun execute(): Response<Result<T>> {
        throw UnsupportedOperationException("Suspend function should not be blocking.")
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel(): Unit = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()
    override fun timeout(): Timeout = Timeout.NONE

    class BodyNotAvailableException : IllegalStateException()
}
