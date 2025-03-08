plugins {
    alias(libs.plugins.io.github.mew22.happntest.compose.lib.plugin)
}

dependencies {
    implementation(projects.feature.artdetail.data)
    implementation(projects.feature.artdetail.domain)
    implementation(projects.feature.artdetail.ui)

    implementation(projects.core.monitoring.gateway)
    implementation(projects.core.ui)
    implementation(projects.core.network)
}