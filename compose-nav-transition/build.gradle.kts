/*
 * MIT License
 *
 * Copyright (c) 2023 Jian James Astrero
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.io.FileInputStream
import java.net.URI
import java.util.*

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(FileInputStream(localPropertiesFile))
    }
}

val ossrhUsername: String? by localProperties
val ossrhPassword: String? by localProperties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("signing")
}

publishing {
    repositories {
        maven {
            val releaseRepo = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
//            val snapshotRepo = "https://s01.oss.sonatype.org/content/repositories/snapshots/"

            name = "OSSRH"
            url = URI.create(releaseRepo)

            credentials {
                username = ossrhUsername
                password = ossrhPassword
            }
        }
    }
    publications {
        create<MavenPublication>("release") {

            groupId = "dev.jianastrero.compose-nav-transitions"
            artifactId = "compose-nav-transitions"
            version = "0.2.0-alpha01"

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name.set("Compose Nav Transitions")
                description.set("A Jetpack Compose Library built on top of the Jetpack Compose Navigation Library to provide easy to use transitions between screens.")
                url.set("https://github.com/jianastrero/compose-nav-transitions/")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/jianastrero/compose-nav-transitions/blob/main/LICENSE")
                        distribution.set("repo")
                    }
                }

                scm {
                    url.set("https://github.com/jianastrero/compose-nav-transitions/")
                    connection.set("scm:git@github.com:jianastrero/compose-nav-transitions.git")
                    developerConnection.set("scm:git@github.com:jianastrero/compose-nav-transitions.git")
                }

                developers {
                    developer {
                        id.set("jianastrero")
                        name.set("Jian James Astrero")
                        email.set("jianjamesastrero@gmail.com")
                        organizationUrl.set("https://jianastrero.dev/")
                    }
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["release"])
}

android {
    namespace = "dev.jianastrero.compose_nav_transition"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.navigation:navigation-compose:2.7.1")

    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
