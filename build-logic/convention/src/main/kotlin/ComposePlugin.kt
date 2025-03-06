import com.android.build.gradle.LibraryExtension
import io.github.mew22.happntest.build_logic.convention.configureComposeAndroid
import io.github.mew22.happntest.build_logic.convention.configureComposeCompiler
import io.github.mew22.happntest.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

class ComposePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.plugins.kotlin.android.get().pluginId)
            apply(libs.plugins.compose.compiler.get().pluginId)
            apply(libs.plugins.android.junit5.plugin.get().pluginId)
            apply(libs.plugins.io.github.mew22.happntest.detekt.plugin.get().pluginId)
        }
        extensions.configure<ComposeCompilerGradlePluginExtension>(::configureComposeCompiler)
    }
}