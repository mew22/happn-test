package io.github.mew22.happntest.build_logic.convention

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

internal fun Project.configureKotlin() {
    dependencies {
        implementation(libs.kotlinx.serialization)
        implementation(libs.kotlinx.collections.immutable)
        implementation(libs.kotlinx.datetime)
        implementation(libs.koin.core)
        implementation(libs.coroutines.core)
        testImplementation(libs.coroutines.test)
        testImplementation(libs.mockk)
        testImplementation(libs.turbine)
        testImplementation(libs.junit.jupiter.api)
        testImplementation(libs.junit.vintage.engine)
        testImplementation(kotlin("test"))
        testImplementation(libs.koin.test)
        testRuntimeOnly(libs.junit.jupiter.engine)
    }

    tasks.withType(Test::class.java).configureEach {
        useJUnitPlatform()
    }
}
