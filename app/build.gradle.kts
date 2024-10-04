import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.adyen.android.assignment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.adyen.android.assignment"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"


        // Define the path to the secrets file
        val secretsFile = file("${rootDir}/secrets.properties")
        if (secretsFile.exists()) {
            // Load the properties from the secrets file
            val properties = Properties()
            FileInputStream(secretsFile).use { fileInputStream ->
                properties.load(fileInputStream)
            }

            buildConfigField("String", "NASA_BASE_URL", "\"https://api.nasa.gov\"")
            buildConfigField("String", "API_KEY", "\"${properties["API_KEY"]}\"")
        } else {
            // Throw an exception if the secrets file is not found
            throw GradleException("Missing secrets.properties file. Please ensure it exists in the project root directory and put your API_KEY key there")
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.google.android.material)
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.coil.compose)

    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    // MockK for unit testing
    testImplementation(libs.mockk)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

