plugins {
    alias(libs.plugins.io.github.mew22.happntest.kotlin.lib.plugin)
}

dependencies {
    implementation(projects.core.env.gateway)
    implementation(libs.retrofit.core)
    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.moshi.converter)
    implementation(libs.moshi)


    testImplementation(libs.okhttp.mock.webserver)
    kspTest(libs.moshi.codegen)
}
