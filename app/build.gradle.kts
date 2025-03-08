plugins {
    alias(libs.plugins.io.github.mew22.happntest.compose.app.plugin)
}

dependencies {
    implementation(libs.material)

    implementation(projects.core.env.gateway)
    implementation(projects.core.env.implementation)
    implementation(projects.core.monitoring.gateway)
    implementation(projects.core.monitoring.implementation)
    implementation(projects.core.network)
    implementation(projects.core.ui)
    implementation(projects.core.db.gateway)
    implementation(projects.core.db.implementation)
    implementation(projects.core.common)

    implementation(projects.feature.artlist.lib)
    implementation(projects.feature.artlist.data)

    implementation(projects.feature.artdetail.lib)
    implementation(projects.feature.artdetail.data)

    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
}