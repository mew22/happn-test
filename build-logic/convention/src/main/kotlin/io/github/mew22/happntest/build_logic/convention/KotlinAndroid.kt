package io.github.mew22.happntest.build_logic.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        namespace = myPackage

        compileSdk = libs.versions.android.compileSdk.get().toInt()

        defaultConfig {
            minSdk = libs.versions.android.minSdk.get().toInt()
        }

        buildFeatures {
            buildConfig = true
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
            isCoreLibraryDesugaringEnabled = true
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }

        flavorDimensions += "version"
        productFlavors {
            create("mock") {
                dimension = "version"
            }

            create("prod") {
                dimension = "version"
            }
        }

        lint {
            disable += "FlowOperatorInvokedInComposition"
        }

        dependencies {
            "coreLibraryDesugaring"(libs.desugaring)
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.appcompat)
            implementation(libs.koin.android)
            androidTestImplementation(libs.junit.jupiter.api)
            androidTestImplementation(libs.androidx.espresso.core)
        }
    }
}
