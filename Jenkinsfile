pipeline {

    agent any

    tools {
        maven 'Maven-3.9.9'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Environment') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
            }
        }

        stage('Build') {
            steps {
               sh 'mvn clean package'
            }
        }
    }

    post {
        success {
            echo 'Build completed successfully!'
        }

        failure {
            echo 'Build failed!'
        }
    }
}