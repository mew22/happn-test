plugins {
    alias(libs.plugins.io.github.mew22.happntest.compose.lib.plugin)
}

dependencies {
    implementation(projects.feature.artlist.data)
    implementation(projects.feature.artlist.domain)
    implementation(projects.feature.artlist.ui)

    implementation(projects.core.monitoring.gateway)
    implementation(projects.core.ui)
    implementation(projects.core.network)
}