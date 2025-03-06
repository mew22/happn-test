package io.github.mew22.happntest.build_logic.convention

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

val Project.libs
    get() = project.extensions.getByName("libs") as LibrariesForLibs

val Project.myPackage
    get() = if (path.contains("app")) "io.github.mew22.happntest"
    else
        path.split(":").drop(1).joinToString(".").let { moduleName ->
            if (moduleName.isNotEmpty()) "io.github.mew22.happntest.$moduleName" else "io.github.mew22.happntest"
        }.apply { logger.info("myPackage: $this") }

fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

fun DependencyHandler.testRuntimeOnly(dependencyNotation: Any): Dependency? =
    add("testRuntimeOnly", dependencyNotation)

fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

fun DependencyHandler.androidTestImplementation(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)

fun DependencyHandler.debugImplementation(dependencyNotation: Any): Dependency? =
    add("debugImplementation", dependencyNotation)