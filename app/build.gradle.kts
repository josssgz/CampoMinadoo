plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.campominadoo"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.campominadoo"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))


    implementation("com.google.firebase:firebase-firestore-ktx")

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    // Dependências básicas - Use os aliases do TOML
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Compose (Usando o BOM declarado no TOML)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    // Room (Use o alias do TOML se estiver definido, ou use a variável para consistência)
    val room_version = "2.6.1" // Mantenha a variável local ou use uma versão no TOML
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    // implementation(libs.androidx.room.ktx) e implementation(libs.androidx.room.common.jvm) são redundantes

    // Outras dependências que você adicionou
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0") // Mantenha, pois não tem alias no TOML
    implementation("androidx.navigation:navigation-compose:2.7.7") // Mantenha

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3") // Mantenha

    // Dependências de teste (parecem OK)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    // ...
}