@file:Suppress("UnstableApiUsage")

include(":app")
include(":ComposeFadingEdges")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        includeBuild("plugins")
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "ComposeFadingEdgesProject"
