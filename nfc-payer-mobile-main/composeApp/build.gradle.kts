import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.net.URI

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    alias(libs.plugins.jetbrains.kotlin.serialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain {
            kotlin.srcDir(layout.buildDirectory.dir("generated/source/svg2compose/kotlin"))
        }
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.ktor.client.okhttp)

            // CameraX dependencies
            implementation(libs.androidx.camera.camera2)
            implementation(libs.androidx.camera.lifecycle)
            implementation(libs.androidx.camera.view)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.jetbrains.compose.navigation)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            api(libs.koin.core)

            implementation(libs.bundles.ktor)
            implementation(libs.bundles.coil)

            implementation(libs.kotlinx.datetime)
        }

        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

tasks.named("prepareComposeResourcesTaskForIosArm64Test") {
    dependsOn("generateFileResources")
}

android {
    namespace = "com.swalisoft.payer"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.swalisoft.payer"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

// Configuration for SVG to Compose task
buildscript {
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        // Add SVG to Compose as a build script dependency
        classpath("com.github.DevSrSouza:svg-to-compose:0.11.0") {
            exclude(group = "com.sun.activation", module = "javax.activation")
            exclude(group = "javax.activation", module = "activation")
            exclude(group = "javax.activation", module = "javax.activation-api")
            exclude(group = "org.ogce", module = "xpp3")
        }
    }
}

tasks.register("generateFileResources") {
    val resourcePath = "${layout.projectDirectory.asFile.absolutePath}/src/commonMain/resources/";
    val assets = listOf("vectors", "icons")
    val outputDir = File("${layout.buildDirectory.get()}/generated/source/svg2compose/kotlin")

    doFirst {
        assets.forEach {
            val assetsDir = File(URI.create("$resourcePath$it").path)

            assetsDir.mkdirs()
        }
        outputDir.mkdirs()
    }

    doLast {
        assets.forEach { asset ->
            val assetsDir = File(URI.create("$resourcePath$asset").path)

            if (assetsDir.exists() && assetsDir.listFiles()?.any { it.extension == "svg" } == true) {
                br.com.devsrsouza.svg2compose.Svg2Compose.parse(
                    applicationIconPackage = "com.swalisoft.payer.assets",
                    accessorName = "Ocr${asset.replaceFirstChar { it.uppercase() }}",
                    outputSourceDirectory = outputDir,
                    vectorsDirectory = assetsDir,
                    type = br.com.devsrsouza.svg2compose.VectorType.SVG,
                    generatePreview = false,
                    allAssetsPropertyName = "AllAssets"
                )
                println("SVG to Compose conversion completed. Files generated in ${outputDir.absolutePath}")
            } else {
                println("No SVG files found in ${assetsDir.absolutePath}")
            }
        }
    }
}


