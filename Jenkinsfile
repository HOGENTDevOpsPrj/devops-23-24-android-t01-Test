pipeline {
    agent any

    environment {
        GRADLE_HOME = tool 'GradleDevOps' 
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-17.0.9.0.9-2.el9.x86_64'
        ANDROID_HOME = '/var/AndroidSDK'
	PATH = "$JAVA_HOME/bin:$GRADLE_HOME/bin:${ANDROID_HOME}/build-tools:${ANDROID_HOME}/platforms:${ANDROID_HOME}/platform-tools:$PATH"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm

		sh "yes | androidsdk 'platforms;android-34'"
		sh "yes | androidsdk 'build-tools;34.0.0'"
		sh "yes | androidsdk 'platform-tools'" 
		sh 'sudo mv -v /home/vagrant/AndroidSDK /var/'
		sh 'sudo chmod -R 777 /var/AndroidSDK'
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
                    sh "export JAVA_HOME=${env.JAVA_HOME}"
                    echo "JAVA_HOME location is: ${env.JAVA_HOME}"
                    sh "export ANDROID_HOME=${env.ANDROID_HOME}"
                    echo "ANDROID_HOME location is: ${env.ANDROID_HOME}"  
                    echo 'Creating a properties files for Gradle to identify which Android SDK to use...'
                    sh "echo sdk.dir=${env.ANDROID_HOME} > local.properties"
		   
		    sh 'chmod +x gradlew'
		    sh "chown -R jenkins:jenkins /var/lib/jenkins/workspace/AndroidApp"
                    // Build the Kotlin project
                    sh 'gradle build --no-daemon build -s'
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


