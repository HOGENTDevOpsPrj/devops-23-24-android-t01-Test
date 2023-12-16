buildscript {
     repositories {
         mavenCentral()
     }
     dependencies {
          classpath 'org.jetbrains.kotlin:kotlin-gradle-plugin:1.2.60'
     }
}

apply plugin: 'kotlin'

repositories {
    // this repo should be available in every subproject that uses kotlin
    mavenCentral() // or jcentrer
}

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


