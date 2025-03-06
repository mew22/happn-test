package io.github.mew22.happntest.build_logic.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureComposeAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
            buildConfig = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.versions.kotlin.get()
        }

        dependencies {
            val bom = libs.androidx.compose.bom
            implementation(platform(bom))
            implementation(libs.androidx.compose.material3)
            implementation(libs.androidx.compose.ui.tooling.preview)
            debugImplementation(libs.androidx.compose.ui.tooling)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.androidx.lifecycle.viewModelCompose)
            implementation(libs.kotlinx.collections.immutable)
            implementation(libs.coil.network.okhttp)
            implementation(libs.coil.compose)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.navigation)

            androidTestImplementation(platform(bom))
            androidTestImplementation(libs.androidx.compose.ui.test)
            debugImplementation(libs.androidx.compose.ui.test.manifest)
        }
    }
}
