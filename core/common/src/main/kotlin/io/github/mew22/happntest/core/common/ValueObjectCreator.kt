package io.github.mew22.happntest.core.common

abstract class ValueObjectCreator<
    in PRIMITIVE,
    out VALUE_CLASS,
    out THROWABLE : IllegalArgumentException,
    > {

    protected abstract val construction: (PRIMITIVE) -> VALUE_CLASS
    open fun getFailure(input: PRIMITIVE): THROWABLE? = null
    abstract fun isValid(input: PRIMITIVE): Boolean

    fun get(input: PRIMITIVE): Result<VALUE_CLASS> = if (isValid(input)) {
        Result.success(value = construction(input))
    } else {
        Result.failure(getFailure(input) ?: IllegalArgumentException("bad input"))
    }

    fun getOrNull(input: PRIMITIVE): VALUE_CLASS? = get(input).getOrNull()

    fun getOrThrow(input: PRIMITIVE): VALUE_CLASS = get(input).getOrThrow()
}
