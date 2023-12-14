pipeline {
    agent any

    environment {
        GRADLE_HOME = tool 'GradleDevOps' // Assumes Gradle is configured as a tool in Jenkins
        PATH = "$PATH:$GRADLE_HOME/bin"
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
