@file:Suppress("UnstableApiUsage")

@Suppress(
    "DSL_SCOPE_VIOLATION",
    "MISSING_DEPENDENCY_CLASS",
    "UNRESOLVED_REFERENCE_WRONG_RECEIVER",
    "FUNCTION_CALL_EXPECTED"
)

plugins {
    id("composefadingedges.application")
}

android {
    val appId = "${ProjectConfig.namespace}.sample"

    namespace = appId

    defaultConfig {
        applicationId = appId
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.version.get()
    }
}

dependencies {
    implementation(projects.composeFadingEdges)

    implementation(libs.compose.color.picker)

    implementation(libs.androidx.ktx)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.compose.ui.util)

    debugImplementation(libs.bundles.debug.compose)
}