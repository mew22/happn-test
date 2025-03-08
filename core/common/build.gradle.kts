plugins {
    alias(libs.plugins.io.github.mew22.happntest.kotlin.lib.plugin)
    `java-test-fixtures`
}

dependencies {
    testFixturesImplementation(libs.turbine)
    testFixturesImplementation(libs.junit.jupiter.api)
    testFixturesImplementation(libs.mockk)
    testFixturesImplementation(libs.coroutines.test)
    testFixturesImplementation(libs.okhttp.mock.webserver)
}