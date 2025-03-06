package io.github.mew22.happntest.build_logic.convention

import org.gradle.api.Project
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag
import kotlin.apply

internal fun Project.configureComposeCompiler(
    ext: ComposeCompilerGradlePluginExtension,
) {
    ext.apply {
        featureFlags.set(
            listOf(
                ComposeFeatureFlag.StrongSkipping, ComposeFeatureFlag.OptimizeNonSkippingGroups
            )
        )
        reportsDestination.set(layout.buildDirectory.dir("compose_compiler"))
        metricsDestination.set(layout.buildDirectory.dir("compose_compiler"))
    }
}
