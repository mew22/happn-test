package io.github.mew22.happntest.core.network.resultadapter

import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class ResultCallAdapter<T>(
    private val bodyType: Type,
    private val moshi: Moshi,
) : CallAdapter<T, Call<Result<T>>> {

    override fun responseType(): Type = bodyType

    override fun adapt(call: Call<T>): Call<Result<T>> = ResultCall(call, moshi)
}
