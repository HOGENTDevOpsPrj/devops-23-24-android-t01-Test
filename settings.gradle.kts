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

repositories {
    // this repo should be available in every subproject that uses kotlin
    mavenCentral() // or jcentrer
}


rootProject.name = "Blanche"
include(":app")


