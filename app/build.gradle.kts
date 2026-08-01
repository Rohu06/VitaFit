plugins {
    alias(libs.plugins.android.application)
}


android {
    namespace = "com.example.vitafit"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.vitafit"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
subprojects {
    configurations.all {
        resolutionStrategy {
            force("androidx.core:core:1.13.0")
            exclude(group = "com.android.support")
        }
    }
}


dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.lottie)
    implementation(libs.shawnlin)
    implementation(libs.circleprogress)
    implementation(libs.mpandroidchart)


}


