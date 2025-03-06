import com.android.build.api.dsl.ApplicationExtension
import io.github.mew22.happntest.build_logic.convention.configureKotlin
import io.github.mew22.happntest.build_logic.convention.configureKotlinAndroid
import io.github.mew22.happntest.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AppPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(libs.plugins.android.application.get().pluginId)
            apply(libs.plugins.kotlin.android.get().pluginId)
            apply(libs.plugins.android.junit5.plugin.get().pluginId)
            apply(libs.plugins.ksp.get().pluginId)
            apply(libs.plugins.io.github.mew22.happntest.detekt.plugin.get().pluginId)
        }

        extensions.configure<ApplicationExtension> {
            defaultConfig {
                applicationId = "io.github.mew22.happntest"
                targetSdk = libs.versions.android.targetSdk.get().toInt()
                versionCode = 1
                versionName = "1.0"

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            buildTypes {
                release {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }
        }
        configureKotlin()
        extensions.configure<ApplicationExtension>(::configureKotlinAndroid)
    }
}