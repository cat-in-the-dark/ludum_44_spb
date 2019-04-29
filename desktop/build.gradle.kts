import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    application
    kotlin("jvm") version "1.3.21"
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

repositories {
    mavenCentral()
    jcenter()
}

application {
    mainClassName = "org.catinthedark.itsadeal.desktop.DesktopLauncher"
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}


sourceSets {
    main {
        resources {
            setSrcDirs(listOf("../game/assets"))
        }
    }
}

val gdxVersion = "1.9.9"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl:$gdxVersion")
    implementation("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop")
    implementation("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-desktop")

    implementation(project(":game"))
}
