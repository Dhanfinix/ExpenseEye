plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.dhandev.rekapin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dhandev.rekapin"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin{
    jvmToolchain(8)
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //koin
    val koinAndroidVersion = "3.5.0"
    implementation("io.insert-koin:koin-android:$koinAndroidVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinAndroidVersion")

    //room
    val roomVersion = "2.5.2"
    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-paging:$roomVersion")

    //datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //navigation
    implementation("androidx.navigation:navigation-compose:2.7.4")

    //lottie
    implementation ("com.airbnb.android:lottie-compose:6.1.0")

    //splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    //data store
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //GSON
    implementation("com.google.code.gson:gson:2.9.1")

    // Coroutine lifecycle scopes
    val lifeCycleScopeVersion = "2.6.2"
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifeCycleScopeVersion")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$lifeCycleScopeVersion")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifeCycleScopeVersion")

    //Number picker
    implementation ("com.chargemap.compose:numberpicker:1.0.3")

    //paging
    implementation ("androidx.paging:paging-runtime-ktx:3.3.0-alpha02")
    implementation ("androidx.paging:paging-compose:3.3.0-alpha02")
}