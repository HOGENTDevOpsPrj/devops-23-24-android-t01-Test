// Top-level build file where you can add configuration options common to all sub-projects/modules.

 compileJava.options.fork = true
 compileJava.options.forkOptions.executable = '/usr/lib/jvm/java-17-openjdk-17.0.9.0.9-2.el9.x86_64'

plugins {
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}
