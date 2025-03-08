package io.github.mew22.happntest.core.network.resultadapter

import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class ResultCallAdapterFactory(private val moshi: Moshi) : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: Retrofit): CallAdapter<*, *> {
        check(getRawType(returnType) == Call::class.java && returnType is ParameterizedType) {
            "Service method must be a suspend function"
        }
        val resultType = getParameterUpperBound(0, returnType)
        check(getRawType(resultType) == Result::class.java && resultType is ParameterizedType) {
            "Service method must return Result<T>"
        }
        val bodyType = getParameterUpperBound(0, resultType)
        return ResultCallAdapter<Any>(bodyType, moshi)
    }
}
