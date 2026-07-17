pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.kikugie.dev/releases")
        maven("https://maven.kikugie.dev/snapshots")
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev")
        maven("https://maven.minecraftforge.net")
        maven("https://maven.neoforged.net/releases/")
    }
}

plugins {
    id("gg.meza.stonecraft") version "1.10.+"
    id("dev.kikugie.stonecutter") version "0.9.+"
}

stonecutter {
    centralScript = "build.gradle.kts"
    kotlinController = true
    shared {
        fun mc(version: String, vararg loaders: String) {
            for (it in loaders) version("$version-$it", version)
        }

        mc("1.16.5", "fabric", "forge")
        mc("1.17.1", "fabric", "forge")
        mc("1.18.2", "fabric", "forge")
        mc("1.19.1", "fabric", "forge")
        mc("1.19.2", "fabric", "forge")
        mc("1.19.3", "fabric", "forge")
        mc("1.19.4", "fabric", "forge")
        mc("1.20.1", "fabric", "forge")
        mc("1.20.2", "fabric", "forge")
        mc("1.20.4", "fabric", "forge")
        mc("1.20.6", "fabric", "neoforge")
        mc("1.21.1", "fabric", "neoforge")
        mc("1.21.3", "fabric", "neoforge")
        mc("1.21.4", "fabric", "neoforge")
        mc("1.21.5", "fabric", "neoforge")
        mc("1.21.8", "fabric", "neoforge")
        mc("1.21.10", "fabric", "neoforge")
        mc("1.21.11", "fabric", "neoforge")
        mc("26.1.2", "fabric", "neoforge")
        mc("26.2", "fabric", "neoforge")

        vcsVersion = "26.2-fabric"
    }
    create(rootProject)
}

rootProject.name = "VineClipper"
