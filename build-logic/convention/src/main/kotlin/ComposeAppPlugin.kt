import com.android.build.api.dsl.ApplicationExtension
import io.github.mew22.happntest.build_logic.convention.configureComposeAndroid
import io.github.mew22.happntest.build_logic.convention.configureComposeCompiler
import io.github.mew22.happntest.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

class ComposeAppPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.plugins.io.github.mew22.happntest.app.plugin.get().pluginId)
            apply(libs.plugins.io.github.mew22.happntest.compose.common.plugin.get().pluginId)
        }
        extensions.configure<ApplicationExtension>(::configureComposeAndroid)
        extensions.configure<ComposeCompilerGradlePluginExtension>(::configureComposeCompiler)
    }
}