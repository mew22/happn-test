plugins {
    alias(libs.plugins.io.github.mew22.happntest.compose.lib.plugin)
}

dependencies {
    implementation(projects.feature.artlist.domain)

    implementation(projects.core.ui)
    implementation(projects.core.monitoring.gateway)

    testImplementation(testFixtures(projects.core.common))
}