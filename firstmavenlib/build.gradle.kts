plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
    signing
}

android {
    namespace = "com.example.firstmavenlib"
    compileSdk = 36

    defaultConfig {
        minSdk = 25

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

publishing {
    publications {
        create<MavenPublication>("release") {
            from(components["java"])

            groupId = "com.yourname"
            artifactId = "helloworld"
            version = "1.0.0"

            pom {
                name.set("Hello World Library")
                description.set("A simple Hello World Kotlin library.")
                url.set("https://github.com/yourname/helloworld-lib")
                licenses {
                    license {
                        name.set("The MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("vivek03codes")
                        name.set("Vivek Yadav")
                        email.set("g21cs.vivek.yadav@gnkhalsa.edu.in")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/yourname/helloworld-lib.git")
                    developerConnection.set("scm:git:ssh://github.com:yourname/helloworld-lib.git")
                    url.set("https://github.com/yourname/helloworld-lib")
                }
            }
        }
    }

    repositories {
        maven {
            name = "OSSRH"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")

            credentials {
                username = project.findProperty("ossrhUsername") as String? ?: ""
                password = project.findProperty("ossrhPassword") as String? ?: ""
            }
        }
    }
}

signing {
    useInMemoryPgpKeys(
        findProperty("signing.keyId") as String,
        findProperty("signing.key") as String,
        findProperty("signing.password") as String
    )
    sign(publishing.publications["release"])
}
