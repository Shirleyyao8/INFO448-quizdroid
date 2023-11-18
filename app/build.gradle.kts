plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "edu.uw.ischool.yuhuiyao.quizdroid"
    compileSdk = 34

    defaultConfig {
        applicationId = "edu.uw.ischool.yuhuiyao.quizdroid"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.preference:preference-ktx:1.2.1")
    implementation ("androidx.preference:preference:1.2.1")
    implementation ("androidx.fragment:fragment-ktx:1.6.2")

    implementation ("androidx.core:core-ktx:1.12.0")
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation ("androidx.multidex:multidex:2.0.1")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
    implementation ("com.google.android.material:material:1.10.0")
    implementation("androidx.preference:preference-ktx:1.2.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}