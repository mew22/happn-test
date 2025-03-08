plugins {
    alias(libs.plugins.io.github.mew22.happntest.android.lib.plugin)
}

dependencies {
    implementation(projects.core.monitoring.gateway)
}