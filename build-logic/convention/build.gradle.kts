import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "io.github.mew22.happntest.build_logic.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    implementation (libs.plugin.detekt)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    // workaround to allow precompiled script to access version catalog
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("kotlinLib") {
            id = "io.github.mew22.happntest.kotlin-lib-plugin"
            implementationClass = "KotlinLibPlugin"
        }
        register("androidLib") {
            id = "io.github.mew22.happntest.android-lib-plugin"
            implementationClass = "AndroidLibPlugin"
        }
        register("compose") {
            id = "io.github.mew22.happntest.compose-plugin"
            implementationClass = "ComposePlugin"
        }
        register("composeLib") {
            id = "io.github.mew22.happntest.compose-lib-plugin"
            implementationClass = "ComposeLibPlugin"
        }
        register("composeApp") {
            id = "io.github.mew22.happntest.compose-app-plugin"
            implementationClass = "ComposeAppPlugin"
        }
        register("app") {
            id = "io.github.mew22.happntest.app-plugin"
            implementationClass = "AppPlugin"
        }
        register("detekt") {
            id = "io.github.mew22.happntest.detekt-plugin"
            implementationClass = "DetektPlugin"
        }
    }
}
