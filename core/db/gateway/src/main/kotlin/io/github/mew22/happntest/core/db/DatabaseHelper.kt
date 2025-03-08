package io.github.mew22.happntest.core.db

interface DatabaseHelper {
    fun <T> create(clazz: Class<T>, name: String): T
}
