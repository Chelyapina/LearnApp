plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.authorization"
    compileSdk = 36

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
    implementation(project(":core:network"))
    implementation(project(":core:security"))

    implementation(project(":shared:designsystem"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.material)
    implementation(platform(libs.androidx.compose.bom))
    implementation (libs.androidx.compose.runtime)
    implementation (libs.androidx.compose.runtime.livedata)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.lifecycle.viewmodel.compose)

    implementation(libs.retrofit.kotlinx.serialization)

    // Dagger 2
    implementation(libs.dagger)
    ksp(libs.dagger.ksp)
}