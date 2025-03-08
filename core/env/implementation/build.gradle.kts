import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.io.github.mew22.happntest.android.lib.plugin)
}

dependencies {
    implementation(projects.core.env.gateway)
    implementation(libs.androidx.core.ktx)
}

android {
    defaultConfig {
        buildConfigField(
            "String",
            "API_KEY",
            gradleLocalProperties(rootDir, providers).getProperty("API_KEY")
        )
    }
}
