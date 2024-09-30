plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

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

    implementation ("org.osmdroid:osmdroid-android:6.1.11")
    //gps-location
    implementation("com.google.android.gms:play-services-location:21.3.0")
    // circle imageview
    implementation("de.hdodenhof:circleimageview:3.1.0")
    //view model
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6")
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    //coroutine
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.core:core-ktx:1.13.1")
    //room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")
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
    testImplementation ("org.hamcrest:hamcrest:2.2")
    testImplementation ("org.hamcrest:hamcrest-library:2.2")
    // AndroidX and Robolectric
    testImplementation ("androidx.test.ext:junit-ktx:1.2.1")
    testImplementation ("androidx.test:core-ktx:1.6.1")
    testImplementation ("org.robolectric:robolectric:4.8")


    //kotlinx-coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
}