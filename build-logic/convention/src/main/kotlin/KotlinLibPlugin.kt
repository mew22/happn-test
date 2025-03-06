import io.github.mew22.happntest.build_logic.convention.configureKotlin
import io.github.mew22.happntest.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test

class KotlinLibPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(libs.plugins.kotlin.jvm.get().pluginId)
            apply(libs.plugins.kotlin.serialization.get().pluginId)
            apply(libs.plugins.ksp.get().pluginId)
            apply(libs.plugins.io.github.mew22.happntest.detekt.plugin.get().pluginId)
        }

        configureKotlin()
    }
}