pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Blanche"
include(":app")

buildscript {
     repositories {
         mavenCentral()
     }
     dependencies {
          classpath 'org.jetbrains.kotlin.android:1.8.10'
     }
}

apply plugin: 'kotlin'

repositories {
    // this repo should be available in every subproject that uses kotlin
    mavenCentral() // or jcentrer
}
