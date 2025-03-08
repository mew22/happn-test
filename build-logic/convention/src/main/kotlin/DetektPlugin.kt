
import io.github.mew22.happntest.build_logic.convention.libs
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.named
import kotlin.text.get

class DetektPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.detekt.get().pluginId)
            }
            extensions.configure<DetektExtension> {
                toolVersion = libs.versions.detekt.get()
                parallel = true
                autoCorrect = true

                tasks.named<Detekt>("detekt") {
                    reports {
                        xml.required.set(true)
                        html.required.set(true)
                        txt.required.set(true)
                        sarif.required.set(true)
                        md.required.set(true)
                    }
                }
            }

            dependencies {
                "detektPlugins"(libs.detekt.formatting)
                "detektPlugins"(libs.detekt.rules.compose)
                "detektPlugins"(libs.detekt.twitter.compose.rules)
            }

            afterEvaluate {
                tasks.named("check") {
                    dependsOn(tasks.named("detektMain"))
                    dependsOn(tasks.named("detektTest"))
                }
            }
            tasks.withType(Detekt::class.java).configureEach {
                exclude { element ->
                    element.file.path.contains("/build/")
                }
            }
        }
    }
}
