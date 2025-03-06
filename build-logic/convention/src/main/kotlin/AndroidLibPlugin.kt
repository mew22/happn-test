import com.android.build.api.dsl.LibraryExtension
import io.github.mew22.happntest.build_logic.convention.configureKotlin
import io.github.mew22.happntest.build_logic.convention.configureKotlinAndroid
import io.github.mew22.happntest.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(libs.plugins.android.library.get().pluginId)
            apply(libs.plugins.kotlin.android.get().pluginId)
            apply(libs.plugins.kotlin.serialization.get().pluginId)
            apply(libs.plugins.android.junit5.plugin.get().pluginId)
            apply(libs.plugins.ksp.get().pluginId)
            apply(libs.plugins.io.github.mew22.happntest.detekt.plugin.get().pluginId)
        }
        configureKotlin()
        extensions.configure<LibraryExtension>(::configureKotlinAndroid)
    }
}