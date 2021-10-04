plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")
    id("io.gitlab.arturbosch.detekt").version("1.18.1")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    compileSdk = 30

    defaultConfig {
        applicationId = "ru.mikov.cats"
        minSdk = 23
        targetSdk = 30
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    val paging_version = "3.0.1"
    val nav_version = "2.3.5"
    val retrofit_version = "2.9.0"
    val okhttp_version = "4.9.0"
    val moshi_version = "1.12.0"
    val lifecycle_version = "2.1.0"
    val livedata_version = "2.2.0-rc02"
    val glide_version = "4.11.0"

    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")

    // navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // pagination
    implementation("androidx.paging:paging-runtime-ktx:$paging_version")

    // viewbinding
    implementation("com.github.kirich1409:viewbindingpropertydelegate:1.4.6")

    // Networking
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit_version")
    implementation("com.squareup.okhttp3:okhttp:$okhttp_version")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttp_version")
    implementation("com.squareup.moshi:moshi-kotlin:$moshi_version")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshi_version")

    // ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-extensions:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$livedata_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")

    // coil
    implementation("com.github.bumptech.glide:glide:$glide_version")
    kapt("com.github.bumptech.glide:compiler:$glide_version")

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

ktlint {
    android.set(true)
    outputColorName.set("GREEN")
}
