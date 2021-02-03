import com.android.build.gradle.internal.tasks.factory.dependsOn

class SimplePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("simplePlugin") {
            println("Hello Plugin")
        }
    }
}

plugins {
    id("com.android.application")
    id("kotlin-android")
}

apply<SimplePlugin>()

android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.3")

    defaultConfig {
        applicationId = "com.inserteffect.app"
        minSdkVersion(16)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            minifyEnabled(false)
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}

val taskA = tasks.register<Copy>("taskA") {
    description = "Copy Stuff"
    from("../a")
    into("../b")
    include("**/*")

    println("taskA")
}

val taskB = tasks.register<Delete>("taskB") {
    description = "Delete Stuff"
    delete("../b")

    println("taskB")
}

val taskC = tasks.register("taskC", Delete::class) {
    description = "Detete Stuff"
    delete("../b")

    println("taskC")
}

val taskE by tasks.registering(Delete::class) {
    description = "Delete stuff"
    delete("../b")

    println("taskE")
}

taskB.dependsOn(taskA)
