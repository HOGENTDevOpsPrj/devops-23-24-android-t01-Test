pipeline {
    agent any

    environment {
        GRADLE_HOME = tool 'GradleDevOps' // Assumes Gradle is configured as a tool in Jenkins
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-17.0.9.0.9-2.el9.x86_64/bin/java'
        PATH = "${JAVA_HOME}/bin:${GRADLE_HOME}/bin:${env.PATH}"
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
                    // Clean previous build artifacts
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

        // Add more stages as needed (e.g., deployment)
    }


}
