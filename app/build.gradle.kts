plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")

}

android {
    namespace = "com.example.weather_app_iti"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weather_app_iti"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
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
}

dependencies {
    //coroutine
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.core:core-ktx:1.13.1")
    // navigation component
    implementation("androidx.navigation:navigation-fragment:2.8.0")
    implementation("androidx.navigation:navigation-ui:2.8.0")
    //room
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Gson
    implementation("com.google.code.gson:gson:2.10.1")
    // Retrofit Gson Converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // circle imageview
    implementation("de.hdodenhof:circleimageview:3.1.0")
    // sdp
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    //ssp
    implementation("com.intuit.ssp:ssp-android:1.1.0")
    //Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")
    //lottie
    implementation("com.airbnb.android:lottie:6.2.0")
    //----------------------------------------------------------
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}