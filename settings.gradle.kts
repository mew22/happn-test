rootProject.name = "happn-test"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
includeBuild("build-logic")
include(":app")
include(
    ":core:common",
    ":core:network",
    ":core:ui",
    ":core:env:gateway",
    ":core:env:implementation",
    ":core:monitoring:gateway",
    ":core:monitoring:implementation",
    ":core:db:gateway",
    ":core:db:implementation",
)
include(
    ":feature:artlist:data",
    ":feature:artlist:domain",
    ":feature:artlist:ui",
    ":feature:artlist:lib",
)
include(
    ":feature:artdetail:data",
    ":feature:artdetail:domain",
    ":feature:artdetail:ui",
    ":feature:artdetail:lib",
)
