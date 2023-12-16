pipeline {
    agent any

    environment {
        GRADLE_HOME = tool 'GradleDevOps' 
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-17.0.9.0.9-2.el9.x86_64'
        ANDROID_HOME = "/home/vagrant/AndroidSDK"
        PATH = "$JAVA_HOME/bin:$GRADLE_HOME/bin:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$PATH"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Clean') {
            steps {
                script {
                    // Clean previous build 
                    sh 'gradle clean'
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    // Build the Kotlin project
                    sh 'gradle build'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Run tests
                    sh 'gradle test'
                }
            }
        }
    }

}


