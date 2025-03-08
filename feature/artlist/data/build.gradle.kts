import io.github.mew22.happntest.build_logic.convention.testImplementation

plugins {
    alias(libs.plugins.io.github.mew22.happntest.kotlin.lib.plugin)
}

dependencies {
    implementation(projects.feature.artlist.domain)

    implementation(libs.retrofit.core)
    implementation(libs.moshi)
    implementation(libs.room.common)
    ksp(libs.room.compiler)
    ksp(libs.moshi.codegen)

    testImplementation(projects.core.network)
}