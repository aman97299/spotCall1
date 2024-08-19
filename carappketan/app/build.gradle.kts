plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.carappketan"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.carappketan"
        minSdk = 25
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
    viewBinding{
        enable = true
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

  //  implementation("com.google.zxing:core:3.4.1")
    //implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    //implementation ("com.journeyapps:zxing-android-legacy:2.3.0@aar")
    //implementation ("com.journeyapps:zxing-android-integration:2.3.0@aar")
    implementation("com.google.zxing:core:3.4.1")
    implementation("com.journeyapps:zxing-android-embedded:4.2.0")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-database:20.3.1")
    implementation("androidx.activity:activity:1.8.0")
    testImplementation("junit:junit:4.13.2")
    implementation("com.google.firebase:firebase-messaging:24.0.0")// msg service
    //implementation ("com.github.ZEGOCLOUD:zego_uikit_prebuilt_call_android:")
    implementation ("com.github.ZEGOCLOUD:zego_uikit_prebuilt_call_android:+") //zegocloud for call and msg
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.cardview:cardview:1.0.0")

}