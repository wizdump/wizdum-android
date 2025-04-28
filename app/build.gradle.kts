import java.util.Properties
val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt)
}

val keystoreProperties = Properties().apply {
    load(File(rootProject.rootDir, "local.properties").inputStream())
}

android {
    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["wizdum_key_alias"] as String
            keyPassword = keystoreProperties["wizdum_key_password"] as String
            storeFile = file(keystoreProperties["wizdum_store_file"] as String)
            storePassword = keystoreProperties["wizdum_store_password"] as String
        }
    }
    namespace = "com.teamwizdum.wizdum"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.teamwizdum.wizdum"
        minSdk = 26
        targetSdk = 34
        versionCode = 4
        versionName = "1.0.02"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", properties.getProperty("kakao_native_app_key"))
        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = properties.getProperty("kakao_redirect_key")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs["release"]
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":designsystem"))
    implementation(project(":data"))
    implementation(project(":feature"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.splashscreen)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.timber)

    implementation(libs.kakao.sdk.user)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.hilt.android)
    implementation(libs.hilt.core)
    compileOnly(libs.ksp.gradle.plugin)
    ksp(libs.hilt.compiler)
}