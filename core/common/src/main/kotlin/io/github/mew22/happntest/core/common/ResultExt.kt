package io.github.mew22.happntest.core.common

import kotlinx.coroutines.CancellationException
import kotlin.reflect.KClass

@Suppress("TooGenericExceptionCaught")
inline fun <T, R> T.runCatchingSafe(block: T.() -> R): Result<R> = try {
    Result.success(block())
} catch (e: CancellationException) {
    throw e
} catch (e: Throwable) {
    Result.failure(e)
}

inline fun <T> Result<T>.mapFailure(
    transform: (Throwable) -> Throwable,
): Result<T> = recoverCatching { throw transform(it) }

inline fun <R, T> Result<T>.flatMap(
    transform: (T) -> Result<R>
): Result<R> = mapCatching {
    transform(it).getOrThrow()
}

inline fun <T, reified E : Throwable> Result<T>.onFailure(
    exception: KClass<E>,
    action: (E) -> Unit,
): Result<T> = onFailure {
    if (exception.isInstance(it) && it is E) action(it)
}

inline fun <T> repeatOnFailure(
    maxAttempts: Int,
    action: () -> Result<T>,
): Result<T> {
    var lastResult = action()
    var count = 1
    while (count < maxAttempts && lastResult.isFailure) {
        lastResult = action()
        count++
    }
    return lastResult
}

fun <T> Result<Result<T>>.flatten(): Result<T> = flatMap { it }
