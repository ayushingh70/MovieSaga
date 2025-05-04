plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.ksp) // for room
    alias(libs.plugins.google.gms.google.services) // for auth
}

android {
    namespace = "com.example.moviesaga"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.moviesaga"
        minSdk = 24
        targetSdk = 35
        versionCode = 2
        versionName = "1.3"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" }

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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //google auth
    implementation(platform(libs.firebase.bom))
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.play.services.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.googleid)
    //viewmode,navigation
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.runtime.livedata)
    //retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)
    //coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation("io.coil-kt:coil-compose:2.4.0")
    //shimmer
    implementation(libs.compose.shimmer)
    implementation("androidx.compose.ui:ui:1.7.8")
    implementation("androidx.compose.ui:ui-text:1.7.8")
    implementation("androidx.compose.material:material:1.7.8")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.3")
    implementation("com.google.firebase:firebase-auth-ktx:23.2.0")
    implementation("androidx.media3:media3-exoplayer:1.6.0")
    implementation("androidx.media3:media3-ui:1.6.0")
    implementation("com.android.volley:volley:1.2.1")

}
