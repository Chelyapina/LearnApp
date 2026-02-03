plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.navigation"
    compileSdk = 36

    buildFeatures {
        compose = true
    }

    defaultConfig {
        minSdk = 24
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
}

dependencies {
    implementation(project(":feature:splash"))
    implementation(project(":feature:deck"))
    implementation(project(":feature:authorization"))
    implementation(project(":core:security"))

    implementation(platform(libs.androidx.compose.bom))
    implementation (libs.androidx.compose.runtime)
    implementation (libs.androidx.compose.runtime.livedata)

    // Dagger 2
    implementation(libs.dagger)
    ksp(libs.dagger.ksp)

    implementation(libs.androidx.navigation.compose)
}