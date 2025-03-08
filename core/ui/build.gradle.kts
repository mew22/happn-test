plugins {
    alias(libs.plugins.io.github.mew22.happntest.compose.lib.plugin)
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.monitoring.gateway)
}