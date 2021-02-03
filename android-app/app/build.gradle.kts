import com.android.build.gradle.internal.tasks.factory.dependsOn

open class SimplePluginExtension {
    var message = "test"
    var description = "I'm a plugin configuration extension"
}

class SimplePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create<SimplePluginExtension>("simplePlugin")

        project.tasks.register("simplePlugin") {
            println("Hello Plugin ${extension.message}")
        }
    }
}

fun Project.simple(configure: SimplePluginExtension.() -> Unit): Unit =
    (this as ExtensionAware).extensions.configure("simplePlugin", configure)

plugins {
    id("com.android.application")
    id("kotlin-android")
}

apply<SimplePlugin>()
apply(from = "tasks.gradle.kts")

simple {
    message = "Configured with custom DSL"
    description = "Some new description"
}

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

    implementation(project(":mylibrary"))
}

/*
Causes eager task creation and configuration

task.register("name") {
    configuration...
}

task.register("name") only registers the task, but creating and configuration happens later,
when invoking the task, or when acquiring a reference with tasks.getByName("name").
*/
//tasks.getByName("taskB").dependsOn(tasks.getByName("taskA"))

// Reference a task without creating and configuration
tasks.named("taskB").dependsOn(tasks.named("taskA"))
